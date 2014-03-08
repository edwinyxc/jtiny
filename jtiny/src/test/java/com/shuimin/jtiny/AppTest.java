package com.shuimin.jtiny;


import javax.servlet.http.HttpServletResponse;

import org.junit.Test;

import com.shuimin.jtiny.core.Action;
import com.shuimin.jtiny.core.Controller;
import com.shuimin.jtiny.core.Request;
import com.shuimin.jtiny.core.View;

public class AppTest {

	@Test
	public void test() {

	}

	public static void main(String[] args) {
		try {
			Y.config().name("test").debug(true).port(8080);
			Y.resources().bind("/", Controller._debug());
			Y.resources().bind("/", new Action(){

				@Override
				protected void exec(Request req, HttpServletResponse resp) throws Exception {
					show(View.Html.one().text("<h1>Hello World!</h1>"));
				}
				
			});
			Y.resources().bind("/test", new Action(){

				@Override
				protected void exec(Request req, HttpServletResponse resp) throws Exception {
					show(View.Html.one().text("<h1>Hello World!///sd</h1>"));
				}
				
			});
			Y.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
