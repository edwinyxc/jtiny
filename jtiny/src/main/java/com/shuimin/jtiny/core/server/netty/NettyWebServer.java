package com.shuimin.jtiny.core.server.netty;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;

import com.shuimin.jtiny.Y;
import com.shuimin.jtiny.core.YServer;

public class NettyWebServer extends YServer {

	private ServerBootstrap bootstrap;

	@Override
	public YServer stop() {
		bootstrap.shutdown();
		return this;
	}

	@Override
	public YServer start() {
		bootstrap = new ServerBootstrap(new NioServerSocketChannelFactory(Executors.newCachedThreadPool(),
				Executors.newCachedThreadPool()));

		// Set up the event pipeline factory.
		bootstrap.setPipelineFactory(new HttpServerPipelineFactory(getUrlRouter()));

		bootstrap.setOption("child.tcpNoDelay", true);

		// Bind and start to accept incoming connections.
		bootstrap.bind(new InetSocketAddress(Y.config().port()));
		return this;
	}
}
