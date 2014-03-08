package com.shuimin.jtiny.core.dispatcher;

import static com.shuimin.base.S._notNull;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.shuimin.base.S;
import com.shuimin.base.S.function.Function;
import com.shuimin.jtiny.Y;
import com.shuimin.jtiny.core.Action;
import com.shuimin.jtiny.core.HttpException;
import com.shuimin.jtiny.core.Request;
import com.shuimin.jtiny.core.YConfig;

public class YDispatcher extends AbstractDispatcher {
	private Function<Request, HttpServletRequest> _resolver;

	public YDispatcher() {
		_resolver = Y.ctx().config().reqResolver();
	}

	final public YDispatcher reqResolver(Function<Request, HttpServletRequest> res) {
		_resolver = res;
		return this;
	}

	protected void doDispatch(final HttpServletRequest request, final HttpServletResponse response) {
		// decode
		String decodedUri = null;
		try {
			decodedUri = URLDecoder.decode(request.getRequestURI(), "UTF-8");
			if (Y.debug()) {
				Y.logger().debug("dispatch:" + decodedUri);
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		Request req = (Request) (request.getAttribute(YConfig.RESOLVED_REQ) == null ?
		// if not found new Request & put it into the raw HttpServletRequest
		new Function<Request, HttpServletRequest>() {
			public Request f(HttpServletRequest a) {
				Request rr = _notNull(_resolver).f(a);
				request.setAttribute(YConfig.RESOLVED_REQ, rr);
				return rr;
			}
		}.f(request)
				// or if this request comes from another, use it
				: request.getAttribute(YConfig.RESOLVED_REQ));
		/*
		 * String uid = _ctx.config().howToGetUID().f(req);
		 * 
		 * 
		 * if (req.cookie(YConfig.USER.USER_LABEL) == null) { Cookie cookie =
		 * new Cookie(YConfig.USER.USER_LABEL, uid); cookie.setPath("");
		 * response.addCookie(cookie); }
		 */
		Y.req(req);
		Y.resp(response);
		try {
			_route(decodedUri).handle(req, response);
		} catch (Exception e) {
			S._lazyThrow(e);
		}

	}

	private Action _route(String path) {
		if (Y.debug()) {
			Y.logger().debug(path);
		}

		Action a = Y.resources().get(path);

		if (a == null) {
			throw new HttpException(404, "[" + path + " ]not found");
		}

		if (Y.debug()) {
			Y.logger().debug("find action " + a.toString());
		}

		return a;
	}
}
