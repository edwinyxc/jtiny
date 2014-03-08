package com.shuimin.jtiny.core;

import javax.servlet.http.HttpServletResponse;

/**
 * 
 * @author ed
 * 
 */
public interface RequestHandler {
	void handle(Request req, HttpServletResponse resp) throws HttpException, Exception;
}
