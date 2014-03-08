package com.shuimin.jtiny.core.http;

import java.net.MalformedURLException;
import java.net.URL;

import javax.servlet.http.Cookie;

import com.shuimin.base.S;
import com.shuimin.base.S.function.Function;

public abstract class AbstractHttpRequest implements HttpRequest {

	@Override
	public String path() {
		S._assert(uri(),"empty uri ");
		try {
			return new URL(uri()).getPath();
		} catch (MalformedURLException e) {
			S._lazyThrow(e);
		}
		return null;
	}

	@Override
	public String header(String string) {
		return headers().get(string);
	}

	@Override
	public String param(String para) {
		String[] ret = params().get(para);
		return ret != null && ret.length > 0 ? ret[0] : null;
	}

	@Override
	public String[] paramArray(String name) {
		return params().get(name);
	}

	@Override
	public boolean isGet() {
		return S._avoidNull(method(), String.class).equalsIgnoreCase("get");
	}

	@Override
	public boolean isPost() {
		return S._avoidNull(method(), String.class).equalsIgnoreCase("post");
	}

	@Override
	public boolean isHead() {
		return S._avoidNull(method(), String.class).equalsIgnoreCase("head");
	}

	@Override
	public boolean isPut() {
		return S._avoidNull(method(), String.class).equalsIgnoreCase("put");
	}

	@Override
	public boolean isDelete() {
		return S._avoidNull(method(), String.class).equalsIgnoreCase("delete");
	}

	@Override
	public Cookie cookie(final String s) {
		return S._for(cookies()).grep(new Function<Boolean, Cookie>() {

			@Override
			public Boolean f(Cookie a) {
				return s.equals(a.getName());
			}
		}).first();
	}

}