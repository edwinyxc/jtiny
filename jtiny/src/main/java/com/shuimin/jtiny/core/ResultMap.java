package com.shuimin.jtiny.core;

import java.util.HashMap;

@SuppressWarnings("serial")
public class ResultMap extends HashMap<String,Object>{
	public final static String RESULT = "result";
	
	public final static String RESULT_OK = "ok";
	
	public final static String RESULT_NO = "no";
	
	public static final ResultMap one() {
		return new ResultMap();
	}
	
	public static final ResultMap ok() {
		return new ResultMap().put(RESULT, RESULT_OK);
	}
	
	public static final ResultMap no() {
		return new ResultMap().put(RESULT, RESULT_NO);
	}
	
	public ResultMap extend(ResultMap m){
		this.putAll(m);
		return this;
	}
	
	@Override
	public ResultMap put(String key, Object value) {
		super.put(key, value);
		return this;
	}
	
	protected ResultMap(){
		
	}
}
