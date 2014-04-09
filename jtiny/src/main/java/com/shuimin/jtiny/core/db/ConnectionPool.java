//package com.shuimin.jtiny.db;
//
//import com.shuimin.base.util.logger.Logger;
//import Server.G;
//import Config;
//import java.lang.reflect.Proxy;
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Properties;
//
//public class ConnectionPool {
//
//	private Logger logger = G.logger();
//	private List<Connection> connPool;
//	private int poolMaxSize = 10;
//
//	private int defaultInitSize;
//	private String driverClass;
//	private String url;
//	private String username;
//	private String pass;
//
//	public void setLogger(Logger logger) {
//		this.logger = logger;
//	}
//
//	public ConnectionPool() {
//
//	}
//
//	public ConnectionPool init(final Properties dbProperties)
//			throws SQLException {
//
//		String temp = (String) dbProperties.get(Config.DB.POOL_MAX_SIZE_KEY);
//		if (temp != null) {
//			poolMaxSize = Integer.parseInt(temp);
//		}
//		logger.debug("max_pool_size ->" + poolMaxSize);
//
//		driverClass = (String) dbProperties.get(Config.DB.DRIVER_CLASS_KEY);
//
//		logger.debug("driver_class ->" + driverClass);
//
//		username = (String) dbProperties.get(Config.DB.USERNAME_KEY);
//
//		logger.debug("username ->" + username);
//
//		pass = (String) dbProperties.get(Config.DB.PASSWORD_KEY);
//
//		logger.debug("password ->" + pass);
//
//		url = (String) dbProperties.get(Config.DB.CONN_URL_KEY);
//
//		logger.debug("conn_url ->" + url);
//		// poolMaxSize = pool_max_size;
//		// userName = user_name;
//		// this.password = password;
//		// this.driverClass = driver_class;
//		// this.url = conn_url;
//		//
//
//		connPool = new ArrayList<Connection>();
//		int size;
//		if (poolMaxSize > defaultInitSize) {
//			size = defaultInitSize;
//		} else {
//			size = poolMaxSize;
//		}
//		for (int i = 0; i < size; i++) {
//			connPool.add(createConnection());
//		}
//
//		return this;
//	}
//
//	private Connection createConnection() throws SQLException {
//		Connection connection;
//		try {
//			Class.forName(driverClass);
//		} catch (ClassNotFoundException e) {
//			e.printStackTrace();
//		}
//		connection = DriverManager.getConnection(url, username, pass);
//		return connection;
//	}
//
//	public synchronized void releaseConnection(Connection connection) {
//		int size = connPool.size();
//		if (size > poolMaxSize) {
//			try {
//				ConnectionProxy handler = (ConnectionProxy) Proxy
//						.getInvocationHandler(connection);
//				handler.getConnection().close();// THE REAL
//				// CLOSE
//			} catch (SQLException e) {
//				if (logger != null) {
//					logger.debug("connection can not close :");
//					logger.debug(connection);
//				}
//				// do nothing
//			}
//			return;
//		}
//		connPool.add(connection);
//	}
//
//	public synchronized Connection getConnection() throws SQLException {
//		// ???????Connection?close()??????
//		// ??????????Connection??????????getConnection()??????????
//		// connectionProxy????????
//		ConnectionProxy connectionProxy = new ConnectionProxy(this);
//		int size = connPool.size();
//		if (connPool.size() == 0 || size > poolMaxSize) {
//			connectionProxy.setConnection(createConnection());
//			return connectionProxy.proxyBind();
//		}
//		Connection connection;
//		int i = 1;
//		for (connection = (Connection) connPool.get(size - i); i <= size; i++) {
//			if (connection.isClosed()) {
//				connPool.remove(size - i);
//			} else {
//				break;
//			}
//		}
//		if (!connection.isClosed()) {
//			connectionProxy.setConnection(connection);
//		} else {
//			connectionProxy.setConnection(createConnection());
//		}
//		return connectionProxy.proxyBind();
//	}
//}
