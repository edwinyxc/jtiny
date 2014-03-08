package com.shuimin.jtiny.helper;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.shuimin.base.S;
import com.shuimin.base.S.function.Callback;

public class HElem {
	public final String type;
	public String html = "";

	public HElem html(String str) {
		html = str;
		return this;
	}

	public Map<String, String> attrs = new HashMap<String, String>();

	public HElem attr(String key, String value) {
		attrs.put(key, value);
		return this;
	}

	public HElem(String type) {
		this.type = type;
	}

	public static HElem fromString(String s) {
		// TODO
		S._fail("do not use this");
		return null;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("<").append(type);
		S._for(attrs).each(new Callback<Map.Entry<String, String>>() {

			@Override
			public void f(Entry<String, String> t) {
				sb.append(" ").append(t.getKey()).append(" = ").append("\"").append(t.getValue()).append("\"");
			}
		});
		sb.append(">");
		sb.append(html);
		sb.append("</").append(type).append(">");
		return sb.toString();
	}
}
