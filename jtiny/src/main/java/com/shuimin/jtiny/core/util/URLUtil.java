package com.shuimin.jtiny.core.util;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLDecoder;

import com.shuimin.base.S;

public class URLUtil {
	public final static int PROTOCOL = 0;
	public final static int HOST = 1;
	public final static int PORT = 2;
	public final static int PATH = 3;
	public final static int QUERY = 4;

	/**
	 * "http://127.0.0.1:8080/??sdf=s&&st=b=&&?sw?=%B9%FA+%BC%D2&tb=&st=9"
	 * 
	 * @return
	 * @throws MalformedURLException
	 * @throws URISyntaxException
	 */
	public static String[] parseURL(String _uri) throws MalformedURLException {
		String[] ret = new String[5];
		String toParse = _uri;
		try {
			toParse = URLDecoder.decode(_uri, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// do nothing
			S._fail(e.getMessage());
		}
		URL url;
		url = new URL(toParse);
		ret[PROTOCOL] = url.getProtocol();
		ret[HOST] = url.getHost();
		ret[PATH] = url.getPath();
		ret[PORT] = String.valueOf(url.getPort());
		ret[QUERY] = url.getQuery();
		return ret;
	}

	/**
	 * @param path
	 * @return
	 */
	public static String[] parsePath(String path) {
		if (path == null || path.length() == 0) {
			return new String[0];// ???
		}
		if (path.charAt(0) == '/') {
			path = path.substring(1);
		}
		String[] tokens = path.split("\\/");
		return tokens;
	}

}


