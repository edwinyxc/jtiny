package com.shuimin.jtiny.core;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Dispatcher {
	public void dispatch(HttpServletRequest req, HttpServletResponse resp);
}
