package com.shuimin.jtiny.core.server.jetty;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.AbstractHandler;

import com.shuimin.base.S;
import com.shuimin.jtiny.Y;
import com.shuimin.jtiny.core.YServer;

/**
 * @author ed
 */
public class JettyServer extends YServer {

	private Server server;

	@Override
	public YServer stop() {
		try {
			server.stop();
		} catch (Exception e) {
			S._lazyThrow(e);
		}
		return this;
	}

	@Override
	public YServer start() {
		server = new Server(port);
		server.setHandler(new AbstractHandler() {
			@Override
			public void handle(String target, Request baseRequest,
					HttpServletRequest request,
					HttpServletResponse response)
					throws IOException, ServletException {

				dispatcher().dispatch(
						new HSRequestWrapper(request),
						new HSResponseWrapper(response));
			}
		});
		try {
			server.start();
		} catch (Exception e) {
			S._lazyThrow(e);
		}
		return this;
	}

}
