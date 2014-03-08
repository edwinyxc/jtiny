package com.shuimin.jtiny.core.view;

import java.io.InputStream;

import javax.servlet.http.HttpServletResponse;

import com.shuimin.base.S;
import com.shuimin.jtiny.core.View;

/**
 * Top abstract View of Blob Value
 * 
 * @author ed
 * 
 */
public class StreamView extends View {
	private final InputStream is;

	protected StreamView(InputStream is) {
		this.is = is;
	}

	@Override
	public void _render(HttpServletResponse resp) throws Exception {
		resp.setCharacterEncoding("UTF-8");
		S.stream.write(is, resp.getOutputStream());
		resp.getWriter().flush();
	}
	
	public static StreamView one(InputStream is){
		return new StreamView(is);
	}
}
