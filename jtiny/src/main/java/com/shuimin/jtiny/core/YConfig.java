package com.shuimin.jtiny.core;

import javax.servlet.http.Cookie;

import com.shuimin.base.S;
import com.shuimin.base.f.Function;
import com.shuimin.base.util.logger.Logger;
import com.shuimin.jtiny.Y;
import com.shuimin.jtiny.core.http.Request;
import java.util.Properties;

/**
 * Global values container
 *
 * @author ed
 *
 */
public class YConfig {

	final public static String RESOLVED_REQ = "wew232sq+_^&^%x*(("; // prevent
	final public static String DEFAULT_ACTION = "_index";
	final public static String COOKIE_LOCALE = "locale";
	
	public static class MODEL {
		public static final String DEFAULT_KEY_ID = "vid";
		
	}
	
	public static class DB {
		public static final String DRIVER_CLASS_KEY = "db.driver_class";
		public static final String USERNAME_KEY = "db.username";
		public static final String PASSWORD_KEY = "db.password";
		public static final String POOL_MAX_SIZE_KEY = "db.pool_max_size";
		public static final String CONN_URL_KEY = "db.conn_url";
	}
	
	public static class USER {

		public static final String USER_LABEL = "uid";
	}

	private String _name = "Y";
	private int _port = 8080;
	private Properties _props;
	
	
//	public Properties props(){
//		return _props;
//	}
	
//	public YConfig props(Properties p){
//		_props = p;
//		return this;
//	}

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

	private Function<String, Request> _howToGetUID = (a) -> {
		Cookie c = a.cookie(USER.USER_LABEL);
		if (c != null) {
			return c.getValue();
		} else {
			return S._notNullElse((String) a.param(USER.USER_LABEL), "guest");
		}
	};

	public Function<String, Request> howToGetUID() {
		return _howToGetUID;
	}

	public YConfig howToGetUID(Function<String, Request> howTo) {
		_howToGetUID = howTo;
		return this;
	}

	private boolean _debug = false;

	public boolean debug() {
		return _debug;
	}

	public YConfig debug(boolean debug) {
		_debug = debug;
		if (debug) {
			Y.logger().config("default", Logger.DEBUG);
		}
		return this;
	}

	public Y end() {
		return Y.get();
	}
}
