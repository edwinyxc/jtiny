package com.shuimin.jtiny.core.view;

import java.io.InputStream;

import com.shuimin.base.S;
import com.shuimin.jtiny.core.View;
import com.shuimin.jtiny.core.http.Response;
import java.io.IOException;

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
	public void _render(Response resp) {
		try {
			S.stream.write(is, resp.out());
		} catch (IOException ex) {
			S._lazyThrow(ex);
		}
		resp.writer().flush();
	}
	
	public static StreamView one(InputStream is) {
		return new StreamView(is);
	}
}
