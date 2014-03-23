package com.shuimin.jtiny;

import javax.servlet.http.HttpServletResponse;

import com.shuimin.base.util.logger.Logger;
import com.shuimin.jtiny.core.ActionTree;
import com.shuimin.jtiny.core.AppCtx;
import com.shuimin.jtiny.core.YActions;
import com.shuimin.jtiny.core.YConfig;
import com.shuimin.jtiny.core.YServer;
import com.shuimin.jtiny.core.dispatcher.YDispatcher;
import com.shuimin.jtiny.core.http.Request;

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
		ctx.server(YServer.netty());
		ctx.resources(new YActions());
		//ctx.connectionPool(new ConnectionPool());
	}

//	static private Properties initConfig() {
//		Properties p = new Properties();
//		try {
//			p.load(Y.class.getClassLoader()
//					.getResourceAsStream("y.config"));
//		} catch (IOException ex) {
//			logger().info("reading config file failed");
//		}
//		return p;
//	}
	private Y() {
	}

	public static YConfig config() {
		return ctx().config();
	}
	/*-----config------*/

	public static boolean debug() {
		return config().debug();
	}

	public static void debug(Throwable e) {
		if (debug()) {
			logger().debug(e);
		}
	}

	public static void debug(Object o) {
		if (debug()) {
			logger().debug(o);
		}
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
		Runtime.getRuntime().addShutdownHook(new Thread() {

			@Override
			public void run() {
				System.out.println("quiting");
				try {
					Y.stop();
				} catch (Exception e) {
					System.out.println("stopping error!");
				}
			}

		});
		ctx.start();
	}

	public static void stop() {
		ctx.stop();
	}

	private static final ThreadLocal<Y> runtimes = new ThreadLocal<Y>();

}
