package com.shuimin.jtiny.core.aop;

/**
 *
 * @author ed
 */
public interface Interceptor {

	public ActionExecution intercept(ActionExecution excution);
}
