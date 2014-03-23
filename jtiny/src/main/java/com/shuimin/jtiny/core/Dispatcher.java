package com.shuimin.jtiny.core;

import com.shuimin.jtiny.core.http.Request;
import com.shuimin.jtiny.core.http.Response;

public interface Dispatcher {

	/**
	 *
	 * @param req
	 * @param resp
	 */
	public void dispatch(Request req, Response resp);
	
	public void localeResolver(LocaleResolver lr);
	
}
