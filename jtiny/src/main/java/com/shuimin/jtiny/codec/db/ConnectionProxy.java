package com.shuimin.jtiny.codec.db;


import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;

public class ConnectionProxy implements InvocationHandler
{

	private Connection connection;
	final private ConnectionPool connPool;

	public ConnectionProxy(ConnectionPool connPool)
	{
		this.connPool = connPool;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
		throws Throwable
	{
		if (method.getName().equals("close")) {
			connPool.releaseConnection((Connection)proxy);
		}
		else {
			return method.invoke(connection, args);
		}
		return null;
	}

	public Connection proxyBind()
	{
		Connection proxyConnection = (Connection) Proxy
			.newProxyInstance(connection.getClass()
				.getClassLoader(),
				new Class<?>[] { Connection.class }, this);
		return proxyConnection;
	}

	public Connection getConnection()
	{
		return connection;
	}

	public void setConnection(Connection connection)
	{
		this.connection = connection;
	}
}