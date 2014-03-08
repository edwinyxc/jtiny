package com.shuimin.jtiny.core.http;

import java.util.Locale;
import java.util.Map;

import javax.servlet.http.Cookie;

/**
 * event
 * 
 * @author ed
 * 
 */
public interface HttpRequest {
	
	String body();

	String path();

	String uri();

	Locale locale();

	Map<String, String> headers();

	String header(String string);

	Map<String, String[]> params();

	String param(String para);

	String[] paramArray(String para);

	String method();

	boolean isGet();

	boolean isPost();

	boolean isHead();

	boolean isPut();

	boolean isDelete();

	String[] remoteIp();

	Iterable<Cookie> cookies();

	Cookie cookie(String s);
}