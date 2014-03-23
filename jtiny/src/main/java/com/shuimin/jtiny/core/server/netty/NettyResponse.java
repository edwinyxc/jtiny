package com.shuimin.jtiny.core.server.netty;

import com.shuimin.jtiny.core.aop.Interrupt;
import com.shuimin.jtiny.core.http.Response;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.ServerCookieEncoder;
import java.io.OutputStream;
import java.io.PrintWriter;
import javax.servlet.http.Cookie;

/**
 *
 * @author ed
 */
public class NettyResponse implements Response {

    private final FullHttpResponse httpResponse;

    private final OutputStream out;

    private final PrintWriter pw;

    public NettyResponse(
        FullHttpResponse httpResponse) {
        this.httpResponse = httpResponse;
        this.out = new NettyOutputStream(httpResponse);
        this.pw = new PrintWriter(out);
    }

    @Override
    public Response header(String k, String v) {
        httpResponse.headers().add(k, v);
        return this;
    }

    @Override
    public void sendError(int code, String msg) {
        HttpResponseStatus status = HttpResponseStatus.valueOf(code);
        httpResponse.setStatus(status);
        writer().println(status.toString());
        Interrupt.on(this).jumpOut();//throw a signal
    }

    @Override
    public Response status(int sc) {
        httpResponse.setStatus(HttpResponseStatus.valueOf(sc));
        return this;
    }

    @Override
    public Response cookie(Cookie c) {
        httpResponse.headers()
            .add(HttpHeaders.Names.SET_COOKIE,
                 ServerCookieEncoder.encode(c.getName(), c.getValue()));
        return this;
    }

    @Override
    public void redirect(String url) {
        httpResponse.setStatus(HttpResponseStatus.MOVED_PERMANENTLY);
        httpResponse.headers().add(HttpHeaders.Names.LOCATION, url);
        Interrupt.on(this).jumpOut();//throw a signal
    }

    @Override
    public Response contentType(String type) {
        httpResponse.headers().add(HttpHeaders.Names.CONTENT_TYPE, type);
        return this;
    }

    @Override
    public OutputStream out() {
        return out;
    }

    @Override
    public PrintWriter writer() {
        return pw;
    }

}
