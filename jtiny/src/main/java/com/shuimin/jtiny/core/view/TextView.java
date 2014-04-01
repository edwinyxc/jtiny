package com.shuimin.jtiny.core.view;


import com.shuimin.jtiny.core.View;
import com.shuimin.jtiny.http.Response;

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
		return (TextView) new TextView("").onRender(
				(resp) -> {
					resp.contentType("text/html;charset=utf8");
				});
	}

	public TextView text(String text) {
		this.text = text;
		return this;
	}

	@Override
	public void _render(Response resp) {
		resp.writer().print(text);
		resp.writer().flush();
	}
}
