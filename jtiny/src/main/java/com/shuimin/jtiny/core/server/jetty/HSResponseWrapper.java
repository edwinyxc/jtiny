package com.shuimin.jtiny.core.server.jetty;

import com.shuimin.base.S;
import com.shuimin.jtiny.Y;
import com.shuimin.jtiny.core.http.Response;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author ed
 */
public class HSResponseWrapper implements Response {

    HttpServletResponse _resp;

    public HSResponseWrapper(HttpServletResponse hsr) {
        _resp = hsr;
    }

    @Override
    public Response header(String k, String v) {
        _resp.addHeader(k, v);
        return this;
    }

    @Override
    public void sendError(int code, String msg) {
        try {
            _resp.sendError(code, msg);
        } catch (IOException ex) {
            S._lazyThrow(ex);
        }
    }

    @Override
    public Response status(int sc) {
        _resp.setStatus(sc);
        return this;
    }

//    @Override
//    public PrintWriter writer() {
//        try {
//            return _resp.getWriter();
//        } catch (IOException ex) {
//            S._lazyThrow(ex);
//            return null;
//        }
//    }
    @Override
    public Response cookie(Cookie c) {
        _resp.addCookie(c);
        return this;
    }

    @Override
    public void redirect(String url) {
        try {
            _resp.sendRedirect(url);
        } catch (IOException ex) {
            S._lazyThrow(ex);
        }
    }

    @Override
    public Response contentType(String type) {
        _resp.setContentType(type);
        return this;
    }

    @Override
    public OutputStream out() {
        try {
            return _resp.getOutputStream();
        } catch (IOException ex) {
            ex.printStackTrace();
            S._lazyThrow(ex);
        }
        return null;
    }

    @Override
    public PrintWriter writer() {
        try {
            return _resp.getWriter();
        } catch (IOException ex) {
            ex.printStackTrace();
            S._lazyThrow(ex);
        }
        return null;
    }

}
