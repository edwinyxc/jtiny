package com.shuimin.jtiny.helper;

public class HtmlHelper {

	public static HElem pre(String a) {
		return new HElem("pre").html(a);
	}

	public static HElem pre(HElem a) {
		return new HElem("pre").html(a.toString());
	}

	public static HElem list(Iterable<?> it) {
		StringBuilder sb = new StringBuilder();
		for (Object a : it) {
			if(a instanceof String)
				sb.append(li((String)a));
			else if (a instanceof HElem)
				sb.append(li((HElem)a));
		}
		return new HElem("ul").html(sb.toString());
	}

	public static HElem li(String html) {
		return new HElem("li").html(html);
	}
	
	public static HElem li(HElem elem) {
		return new HElem("li").html(elem.toString());
	}
	
	public static HElem a(String text){
		return new HElem;
	}
	
	public static String a(String txt) {
		
	}
}
