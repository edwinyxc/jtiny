package com.shuimin.jtiny;

import javax.servlet.http.HttpServletResponse;

import com.shuimin.base.util.logger.Logger;
import com.shuimin.jtiny.core.ActionTree;
import com.shuimin.jtiny.core.AppCtx;
import com.shuimin.jtiny.core.Request;
import com.shuimin.jtiny.core.YActions;
import com.shuimin.jtiny.core.YConfig;
import com.shuimin.jtiny.core.YDispatcher;
import com.shuimin.jtiny.core.server.JettyServer;

/**
 * API
 * 
 * @author ed
 * 
 */
public class Y {

	private Request req;
	private HttpServletResponse resp;
	private ActionTree tree_node;

	public static final AppCtx ctx = AppCtx.instance();

	// init
	static {
		ctx.config(new YConfig());
		ctx.dispatcher(new YDispatcher());
		ctx.server(new JettyServer());
		ctx.resources(new YActions());
	}

	private Y() {
	}

	public static YConfig config() {
		return ctx().config();
	}

	public static boolean debug() {
		return ctx().config().debug();
	}

	public static Logger logger() {
		return ctx().logger();
	}

	public static AppCtx ctx() {
		return Y.ctx;
	}

	public static YActions resources() {
		return ctx().resources();
	}

	/**
	 * only initialized in dispatcher
	 * 
	 * @param y
	 */
	public static void init() {

	}

	public static Y get() {
		Y rt = runtimes.get();
		if (rt == null) {
			rt = new Y();
		}
		runtimes.set(rt);
		return rt;
	}

	public static Request req() {
		return get().req;
	}

	public static void req(Request req) {
		get().req = req;
	}

	public static HttpServletResponse resp() {
		return get().resp;
	}

	public static void resp(HttpServletResponse resp) {
		get().resp = resp;
	}

	public static ActionTree tree_node() {
		return get().tree_node;
	}

	public static void tree_node(ActionTree tree_node) {
		get().tree_node = tree_node;
	}

	public static void start() {
		ctx.start();
	}

	public static void stop() {
		ctx.stop();
	}

	private static final ThreadLocal<Y> runtimes = new ThreadLocal<Y>();

}
