package com.shuimin.jtiny.http;

import javax.servlet.http.Cookie;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;
import java.util.Map;

/**
 * event
 *
 * @author ed
 *
 */
public interface Request {

    InputStream in() throws IOException;

    String path();

    String uri();

    Locale locale();

    Map<String, String[]> headers();

    String[] header(String string);

    Map<String, String[]> params();

    String param(String para);

    Request param(String para, String value);

    String[] paramArray(String para);

    String method();

    String remoteIp();

    Iterable<Cookie> cookies();

    Cookie cookie(String s);
}
