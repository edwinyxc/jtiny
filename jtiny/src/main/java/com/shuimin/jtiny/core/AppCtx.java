package com.shuimin.jtiny.core;

import com.shuimin.base.S;
import com.shuimin.base.util.logger.Logger;
import com.shuimin.jtiny.db.ConnectionPool;
import java.util.Properties;

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

	private ConnectionPool _cp;
	private Logger _logger = S.logger();
	private YConfig _config;
	private Dispatcher _dispatcher;
	private YActions _resources;
	private YServer _server;

	public Logger logger() {
		return _logger;
	}

	public AppCtx logger(Logger logger) {
		_logger = logger;
		return this;
	}

	public YConfig config() {
		return _config;
	}

	public AppCtx config(YConfig config) {
		_config = config;
		return this;
	}

	public Dispatcher dispatcher() {
		return _dispatcher;
	}

	public AppCtx dispatcher(Dispatcher dispatcher) {
		_dispatcher = dispatcher;
		return this;
	}

	public YActions resources() {
		return _resources;
	}

	public AppCtx resources(YActions resources) {
		_resources = resources;
		return this;
	}

	public AppCtx server(YServer server) {
		_server = server;
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

	public AppCtx connectionPool(ConnectionPool cp) {
		_cp = cp;
		return this;
	}

	public ConnectionPool connectionPool() {
		return _cp;
	}

}
