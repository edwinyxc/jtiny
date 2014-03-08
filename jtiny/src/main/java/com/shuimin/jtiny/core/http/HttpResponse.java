package com.shuimin.jtiny.core.http;

import java.io.OutputStream;

import javax.servlet.http.Cookie;

public interface HttpResponse {
	
	HttpResponse header(String k, String v);

	OutputStream out();

	void sendError(int code, String msg);
	
	HttpResponse cookie(Cookie c);
}
