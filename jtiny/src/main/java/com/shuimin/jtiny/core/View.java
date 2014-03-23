package com.shuimin.jtiny.core;

import com.shuimin.base.f.Callback;
import com.shuimin.jtiny.core.http.Response;
import java.io.InputStream;

import com.shuimin.jtiny.core.view.StreamView;
import com.shuimin.jtiny.core.view.TextView;

/**
 *
 * @author ed
 *
 */
public abstract class View {

	private Callback<Response> _before
			= new Callback<Response>() {

				@Override
				public void apply(Response t) {
				}
			};

	public final void render(Response resp) {
		_before.apply(resp);
		_render(resp);
	}

	public final View onRender(Callback<Response> before) {
		_before = before;
		return this;
	}

	protected abstract void _render(Response resp);

	public static class Text {

		public static TextView one() {
			return TextView.one();
		}
	}

	public static class Html {

		public static TextView one() {
			return (TextView) TextView.one().onRender((Response resp) -> {
				resp.contentType("text/html;charset=utf8");
			});
		}
	}

	public static class Blob {

		public static View one(InputStream is) {
			return StreamView.one(is);
		}
	}

}
