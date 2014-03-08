package com.shuimin.jtiny.core;

@SuppressWarnings("serial")
public class HttpException extends RuntimeException {
	private int code;
	
	public int code(){
		return code;
	}
	
	public HttpException() {
		super("error");
		this.code = 500;
	}
	
	public HttpException(Exception e) {
		super(e.getMessage());
		this.code = 500;
	}

	public HttpException(int code, String errMsg) {
		super(errMsg);
		this.code = code;
	}
}
