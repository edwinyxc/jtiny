package com.shuimin.jtiny.core;

import java.io.InputStream;

import javax.servlet.http.HttpServletResponse;

import com.shuimin.base.S.function.Callback;
import com.shuimin.jtiny.core.view.StreamView;
import com.shuimin.jtiny.core.view.TextView;

/**
 * 
 * @author ed
 * 
 */
public abstract class View {

	private Callback<HttpServletResponse> _before = new Callback<HttpServletResponse>() {

		@Override
		public void f(HttpServletResponse t) {
		}
	};

	protected final void render(HttpServletResponse resp) throws Exception {
		_before.f(resp);
		_render(resp);
	};

	public final View onRender(Callback<HttpServletResponse> before) {
		_before = before;
		return this;
	}

	protected abstract void _render(HttpServletResponse resp) throws Exception;
	
	public static class Text{
		public static TextView one(){
			return TextView.one();
		}
	}
	
	public static class Html{
		public static TextView one(){
			return (TextView) TextView.one().onRender(new Callback<HttpServletResponse>() {
				
				@Override
				public void f(HttpServletResponse resp) {
					resp.setContentType("text/html;charset=utf8");
				}
			});
		}
	}
	
	public static class Blob{
		public static View one(InputStream is){
			return StreamView.one(is);
		}
	}
	
}
