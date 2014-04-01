package com.shuimin.jtiny.server.netty;

import com.shuimin.base.S;
import com.shuimin.jtiny.RequestHandler;
import com.shuimin.jtiny.Server;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpContentCompressor;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.stream.ChunkedWriteHandler;

public class NettyServer implements Server {

    private ServerBootstrap bootstrap;
    private ChannelFuture severFuture;

    EventLoopGroup bossGroup = new NioEventLoopGroup();
    EventLoopGroup workerGroup = new NioEventLoopGroup();

    @Override
    public void listen(int port) {

        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch)
                        throws Exception {
                            ch.pipeline()
                            .addLast("codec", new HttpServerCodec())
                            .addLast(new HttpObjectAggregator(1048576))
                            .addLast(new HttpContentCompressor())
                            .addLast("chunkedWriter", new ChunkedWriteHandler())
                            .addLast(new HttpServerHandler(reqHandler));
                        }
                    })
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);

            // Bind and start to accept incoming connections.
            severFuture = b.bind(port).sync();

            // Wait until the server socket is closed.
            // In this example, this does not happen, 
            // but you can do that to gracefully
            // shut down your server.
            severFuture.channel().closeFuture().sync();//block here?
        } catch (InterruptedException ex) {
            S._lazyThrow(ex);
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }

        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void stop() {
        if (bootstrap != null) {
            try {
                severFuture.channel().closeFuture().sync();
            } catch (InterruptedException ex) {
                S._lazyThrow(ex);
            }
        }
    }

    private RequestHandler reqHandler;

    @Override
    public Server use(RequestHandler handler) {
        reqHandler = handler;
        return this;
    }

}
