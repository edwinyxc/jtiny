//package com.shuimin.jtiny.db;
//
//
//import java.lang.reflect.InvocationHandler;
//import java.lang.reflect.Method;
//import java.lang.reflect.Proxy;
//import java.sql.Connection;
//
//// ??InvocationHandler????????????
//// ??????li
//public class ConnectionProxy implements InvocationHandler
//{
//
//	private Connection connection;
//	final private ConnectionPool connPool;
//
//	public ConnectionProxy(ConnectionPool connPool)
//	{
//		this.connPool = connPool;
//	}
//
//	@Override
//	public Object invoke(Object proxy, Method method, Object[] args)
//		throws Throwable
//	{
//		// ?????close(),????????releaseConnection??????
//		if (method.getName().equals("close")) {
////			 System.out.println("before invoke !");
//			connPool.releaseConnection((Connection)proxy);
////			 System.out.println("after invoke !");
//		}
//		else {
//			// ??close()??????????????
//			return method.invoke(connection, args);
//		}
//		return null;
//	}
//
//	public Connection proxyBind()
//	{
//		// ????????????????
//		// newProxyInstance() arg0-->??????????
//		// newProxyInstance() arg1-->????????????
//		// newProxyInstance()
//		// arg2-->?????????????????????this???invoke())
//		Connection proxyConnection = (Connection) Proxy
//			.newProxyInstance(connection.getClass()
//				.getClassLoader(),
//				new Class<?>[] { Connection.class }, this);
//		return proxyConnection;
//	}
//
//	public Connection getConnection()
//	{
//		return connection;
//	}
//
//	public void setConnection(Connection connection)
//	{
//		this.connection = connection;
//	}
//}