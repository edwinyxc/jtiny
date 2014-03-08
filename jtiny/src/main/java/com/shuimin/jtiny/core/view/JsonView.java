package com.shuimin.jtiny.core.view;


import com.alibaba.fastjson.JSON;
import com.shuimin.base.S;
import com.shuimin.jtiny.core.ResultMap;

public class JsonView extends TextView {
	protected JsonView(ResultMap map) {
		super(JSON.toJSONString(S._notNull(map)));
	}

	protected JsonView() {
		super("");
	}

	public static JsonView one(ResultMap map) {
		return new JsonView(map);
	}

	public JsonView of(ResultMap map) {
		return new JsonView(map);
	}
}
