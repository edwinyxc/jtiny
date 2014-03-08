package com.shuimin.jtiny.core;

public class HttpMethod {
	public static final int GET = 1;
	public static final int POST = 2;
	public static final int PUT = 4;
	public static final int DELETE = 8;

	public static boolean allow(int cri, int test) {
		return ((test & cri) == cri);
	}

	public static int fromString(String method) {
		if (method == null)
			return 0;
		if (method.equalsIgnoreCase("get"))
			return GET;
		if (method.equalsIgnoreCase("post"))
			return POST;
		if (method.equalsIgnoreCase("put"))
			return PUT;
		if (method.equalsIgnoreCase("delete"))
			return DELETE;
		return 0;
	}
}
