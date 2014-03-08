package com.shuimin.jtiny.core;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.shuimin.jtiny.Y;
import com.shuimin.jtiny.helper.HtmlHelper;

/**
 * 
 * @author ed
 * 
 */
public abstract class Controller extends Action {

	protected Map<String, Action> _actions = new HashMap<String, Action>();

	public Controller action(int method, Action a) {
		_actions.put(a._name, a._withController(this));
		return this;
	}

	public Action action(String name) {
		return _actions.get(name);
	}
	
	protected Action index(){
		return new Action("index") {
			
			@Override
			protected void exec(Request req, HttpServletResponse resp) throws Exception {
				
			}
		};
	} 
	
	@Override
	final protected void exec(Request req, HttpServletResponse resp) throws Exception {
		index().exec(req, resp);
	}

	public static Controller _debug() {
		return new Controller() {
		}.action(HttpMethod.GET, new Action("_dbg_tree") {

			@Override
			protected void exec(Request req, HttpServletResponse resp) throws Exception {
				show(View.Html.one().text(HtmlHelper.pre(Y.tree_node().root().toString()).toString()));
			}
		});
	}

	public static Controller one() {
		return new Controller() {
		};
	}
}
