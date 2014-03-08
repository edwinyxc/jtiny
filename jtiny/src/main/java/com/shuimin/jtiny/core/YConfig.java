package com.shuimin.jtiny.core;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import com.shuimin.base.S;
import com.shuimin.base.S.function.Function;
import com.shuimin.base.util.logger.Logger;
import com.shuimin.jtiny.Y;

/**
 * Global values container
 * 
 * @author ed
 * 
 */
public class YConfig {

	final public static String RESOLVED_REQ = "wew232sq+_^&^%x*(("; // prevent
	final public static String DEFAULT_ACTION = "_index";

	public static class USER {
		public static final String USER_LABEL = "uid";
	}

	private String _name = "Y";
	private int _port = 8080;

	public int port() {
		return _port;
	}

	public YConfig port(int port) {
		_port = port;
		return this;
	}

	public YConfig name(String name) {
		_name = name;
		return this;
	}

	public String name() {
		return _name;
	}

	/****** request resolver *******/
	private Function<Request, HttpServletRequest> _reqResolver = new Function<Request, HttpServletRequest>() {

		@Override
		public Request f(HttpServletRequest request) {
			return Request.of(request);
		}
	};

	private Function<String, Request> _howToGetUID = new Function<String, Request>() {

		@Override
		public String f(Request a) {
			Cookie c = a.cookie(USER.USER_LABEL);
			if (c != null) {
				return c.getValue();
			} else {
				return S._notNullElse((String) a.param(USER.USER_LABEL), "guest");
			}
		}

	};

	public Function<Request, HttpServletRequest> reqResolver() {
		return _reqResolver;
	}

	public Function<String, Request> howToGetUID() {
		return _howToGetUID;
	}

	public YConfig howToGetUID(Function<String, Request> howTo) {
		_howToGetUID = howTo;
		return this;
	}

	public YConfig reqResolver(Function<Request, HttpServletRequest> resolver) {
		_reqResolver = resolver;
		return this;
	}

	private boolean _debug = false;

	public boolean debug() {
		return _debug;
	}

	public YConfig debug(boolean _) {
		_debug = _;
		if (_)
			Y.logger().config("default", Logger.DEBUG);
		return this;
	}

	public Y end() {
		return Y.get();
	}
}
