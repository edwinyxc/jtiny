package com.shuimin.jtiny.core;

import static com.shuimin.base.S._for;

import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import com.shuimin.base.S;
import com.shuimin.base.S.function.Callback;
import com.shuimin.base.S.function.Function;

public final class Request {
	final private HttpServletRequest _rawReq;

	final private Map<String, Object> _params;

	protected Request(HttpServletRequest req) {
		_rawReq = S._notNull(req);

		// TODO:support multi-params
		_params = _for(req.getParameterMap()).<Object> map(new Function<Object, String[]>() {

			@Override
			public Object f(String[] a) {
				if (a != null && a.length > 0)
					return a[0];
				return null;
			}
		}).val();

		S._for(req.getAttributeNames()).each(new Callback<String>() {

			@Override
			public void f(String name) {
				param(name, _rawReq.getAttribute(name));
			}
		});

	}

	public HttpServletRequest raw() {
		return _rawReq;
	}

	public static Request of(HttpServletRequest req) {
		return new Request(req);
	}

	public Request param(String name, Object s) {
		_params.put(name, s);
		return this;
	}

	@SuppressWarnings("unchecked")
	public <E> E param(String name) {
		return (E) _params.get(name);
	}

	public Cookie[] cookies() {
		return _rawReq.getCookies();
	}

	public Cookie cookie(final String name) {
		return S._for(_rawReq.getCookies()).grep(new Function<Boolean, Cookie>() {

			@Override
			public Boolean f(Cookie a) {
				return name.equals(a.getName());
			}
		}).first();
	}
}
