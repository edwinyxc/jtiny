package com.shuimin.jtiny.core;

/**
 * 
 */
public abstract class YServer {
	protected int _port = 8080;

	final public YServer port(int port) {
		_port = port;
		return this;
	}

	public abstract YServer stop();

	public abstract YServer start();

}
