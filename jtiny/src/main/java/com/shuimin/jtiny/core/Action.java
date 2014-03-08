package com.shuimin.jtiny.core;

import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.shuimin.base.S;
import com.shuimin.base.S.function.Callback;

public abstract class Action implements RequestHandler {

	protected String _name;

	protected Controller _controller;

	protected int _method = 0;

	public Action method(int method) {
		_method = method;
		return this;
	}

	public Action allow(String method) {
		if (HttpMethod.allow(_method, HttpMethod.fromString(method))) {
			return this;
		} else
			throw new HttpException(406, "use only [get, post, delete, put]");
	}

	protected Action() {
		_name = "unknown_action";
	}

	protected Action(String name) {
		_name = name;
	}

	public String name() {
		return _name;
	}

	public Action name(String _) {
		_name = _;
		return this;
	}

	protected Action _withController(Controller controller) {
		_controller = controller;
		return this;
	}

	final private List<Callback<Request>> _onProcess = new LinkedList<Callback<Request>>();

	final public Action onProcess(Callback<Request>... cb) {

		for (Callback<Request> c : cb) {
			_onProcess.add(c);
		}

		return this;
	}

	final protected void redirect(String uri) throws RedirectInterception {
		throw new RedirectInterception(uri);
	}

	final protected void forward(String uri) throws ForwardInterception {
		throw new ForwardInterception(uri);
	}

	final protected void show(View view) throws ViewInterception {
		throw new ViewInterception(view);
	}

	@Override
	public final void handle(final Request req, HttpServletResponse resp) throws Exception {
		for (Callback<Request> cb : _onProcess) {
			cb.f(req);
		}
		try {
			exec(req, resp);
		} catch (ForwardInterception f) {
			req.raw().getRequestDispatcher(f._uri).forward(req.raw(), resp);
			return;
		} catch (RedirectInterception d) {
			resp.sendRedirect(d._uri);
			return;
		} catch (ViewInterception v) {
			v._view.render(resp);
		}
	}

	protected abstract void exec(Request req, HttpServletResponse resp) throws Exception;

	@SuppressWarnings("serial")
	class RedirectInterception extends Exception {
		final private String _uri;

		public RedirectInterception(String uri) {
			_uri = S._notNull(uri);
		}
	}

	@SuppressWarnings("serial")
	class ViewInterception extends Exception {
		final private View _view;

		public ViewInterception(View view) {
			_view = S._notNull(view);
		}
	}

	@SuppressWarnings("serial")
	class ForwardInterception extends Exception {
		final private String _uri;

		public ForwardInterception(String uri) {
			_uri = S._notNull(uri);
		}
	}

	public String toString() {
		return S._avoidNull(_controller, Controller.class).name() + "#" + _name;
	}
}