package com.shuimin.jtiny.core.server.netty;

import com.shuimin.jtiny.Y;
import com.shuimin.jtiny.core.Dispatcher;
import com.shuimin.jtiny.core.aop.Interrupt;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import static io.netty.handler.codec.http.HttpHeaders.*;
import static io.netty.handler.codec.http.HttpHeaders.Names.*;
import static io.netty.handler.codec.http.HttpHeaders.Values.*;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import io.netty.handler.codec.http.HttpVersion;

public class HttpServerHandler
    extends SimpleChannelInboundHandler<FullHttpRequest> {

    final Dispatcher dispather; // THE DISPATCHER

    public HttpServerHandler(Dispatcher dispatcher) {
        this.dispather = dispatcher;
    }

    @Override
    protected void messageReceived(ChannelHandlerContext ctx,
                                   FullHttpRequest msg)
        throws Exception {
        FullHttpRequest f_req = msg;
        FullHttpResponse f_resp
            = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, OK,
                                          Unpooled.buffer());

        try {
            dispather.dispatch(new NettyRequest(f_req, ctx.channel()),
                               new NettyResponse(f_resp));
            Y.debug("after dispatch");
            Y.debug(f_resp.getStatus());
            Y.debug(f_resp.headers());
            Y.debug(f_resp.content());

            if (isKeepAlive(f_req)) {
                //length
                f_resp.headers().set(
                    CONTENT_LENGTH, f_resp.content().readableBytes());
                f_resp.headers().set(CONNECTION, KEEP_ALIVE);
            }
            ChannelFuture last = ctx.write(f_resp);
            // Write the end marker
            if (!isKeepAlive(f_req)) {
                // Close the connection when the whole content is written out.
                last.addListener(ChannelFutureListener.CLOSE);
            }

        } catch (Interrupt.JumpInterruption jump) {
            //catch jump 
            Y.debug(jump);
            Y.debug(f_resp.getStatus());
            Y.debug(f_resp.headers());
            ctx.writeAndFlush(f_resp).addListener(ChannelFutureListener.CLOSE);
        } finally {
            // Write the end marker
            ctx.flush();
        }
    }

}
