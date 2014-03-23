package com.shuimin.jtiny.core.http;

import java.io.OutputStream;
import java.io.PrintWriter;
import javax.servlet.http.Cookie;

public interface Response {

    Response header(String k, String v);

//	OutputStream out() throws IOException;
    void sendError(int code, String msg);

    Response status(int sc);

    OutputStream out();

    PrintWriter writer();

    Response cookie(Cookie c);

    void redirect(String url);

    Response contentType(String type);
}
