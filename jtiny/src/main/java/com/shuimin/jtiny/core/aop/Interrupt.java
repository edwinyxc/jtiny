package com.shuimin.jtiny.core.aop;

import com.shuimin.base.S;
import com.shuimin.jtiny.core.View;
import com.shuimin.jtiny.core.YException;

/**
 *
 * @author ed
 */
public class Interrupt {

	public Object caller;

	private Interrupt(Object caller) {
		this.caller = caller;
	}

	public static Interrupt on(Object o) {
		return new Interrupt(o);
	}

	public void redirect(String uri) {
		throw new RedirectInterruption(caller, uri);
	}

	public void render(View view) {
		throw new RenderViewInterruption(caller, view);
	}

	public void jumpOut() {
		throw new JumpInterruption(caller);
	}

	/**
	 * used in a interception chain to jump out the chain
	 */
	@SuppressWarnings("serial")
	public static class JumpInterruption extends YException {

		public JumpInterruption(Object cause) {
			super(cause);
		}

		@Override
		public String brief() {
			return "jump out ";
		}

		@Override
		public String detail() {
			return "jump out from " + cause();
		}

	}

	@SuppressWarnings("serial")
	public static class RedirectInterruption extends YException {

		final private String _uri;

		public String uri() {
			return _uri;
		}

		public RedirectInterruption(Object o, String uri) {
			super(o);
			_uri = S._notNull(uri);
		}

		@Override
		public String brief() {
			return "redirect to " + _uri;
		}

		@Override
		public String detail() {
			return brief() + " fired by " + cause();
		}
	}

	@SuppressWarnings("serial")
	public static class RenderViewInterruption extends YException {

		final private View _view;

		public View view() {
			return _view;
		}

		public RenderViewInterruption(Object cause, View view) {
			super(cause);
			_view = S._notNull(view);
		}

		@Override
		public String brief() {
			return "render view " + _view;
		}

		@Override
		public String detail() {
			return brief() + " fired by " + cause();
		}
	}

}
