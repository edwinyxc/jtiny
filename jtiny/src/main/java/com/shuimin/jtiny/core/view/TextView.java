package com.shuimin.jtiny.core.view;

import javax.servlet.http.HttpServletResponse;

import com.shuimin.base.S.function.Callback;
import com.shuimin.jtiny.core.View;

/**
 * Top abstract View of Text Value
 * 
 * @author ed
 * 
 */
public class TextView extends View {
	private String text;

	protected TextView(String text) {
		this.text = text;
	}

	public static TextView one() {
		return (TextView) new TextView("").onRender(new Callback<HttpServletResponse>() {
			
			@Override
			public void f(HttpServletResponse resp) {
				resp.setCharacterEncoding("UTF-8");
				resp.setContentType("text/html;charset=utf8");
			}
		});
	}

	public TextView text(String text) {
		this.text = text;
		return this;
	}

	@Override
	public void _render(HttpServletResponse resp) throws Exception {
		resp.getWriter().print(text);
		resp.getWriter().flush();
	}
}


