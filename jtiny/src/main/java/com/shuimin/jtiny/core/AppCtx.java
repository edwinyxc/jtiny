package com.shuimin.jtiny.core;

import com.shuimin.base.S;
import com.shuimin.base.util.logger.Logger;

/**
 * Application context
 * 
 * @author ed
 * 
 */
public final class AppCtx {

	private AppCtx() {
	}

	public final static class holder {
		final private static AppCtx instance = new AppCtx();
	}

	public static AppCtx instance() {
		return holder.instance;
	}

	private Logger _logger = S.logger();
	private YConfig _config;
	private YDispatcher _dispatcher;
	private YActions _resources;
	private YServer _server;

	public Logger logger() {
		return _logger;
	}

	public AppCtx logger(Logger _) {
		_logger = _;
		return this;
	}

	public YConfig config() {
		return _config;
	}

	public AppCtx config(YConfig _) {
		_config = _;
		return this;
	}

	public YDispatcher dispatcher() {
		return _dispatcher;
	}

	public AppCtx dispatcher(YDispatcher _) {
		_dispatcher = _;
		return this;
	}

	public YActions resources() {
		return _resources;
	}

	public AppCtx resources(YActions _) {
		_resources = _;
		return this;
	}

	public AppCtx server(YServer _) {
		_server = _;
		return this;
	}

	public static AppCtx newInstance() {
		return new AppCtx();
	}

	public void start() {
		_server.start();
	}

	public void stop() {
		_server.stop();
	}

}
