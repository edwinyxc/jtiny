package com.shuimin.jtiny.core;

import com.shuimin.jtiny.Y;
import com.shuimin.jtiny.core.server.jetty.JettyServer;
import com.shuimin.jtiny.core.server.netty.NettyServer;

/**
 *
 */
public abstract class YServer {

	protected int port = 8080;

	final public YServer port(int port) {
		this.port = port;
		return this;
	}

	protected Dispatcher dispatcher() {
		return Y.ctx.dispatcher();
	}

	public abstract YServer stop();

	public abstract YServer start();

	public static YServer Jetty() {
		return new JettyServer();
	}

	public static YServer netty() {
		return new NettyServer();
	}

}
