package com.cp.utils.xml;

public class SQLPoolException extends Exception{
	
	public SQLPoolException() {
	}
	
	public SQLPoolException(int code, String msg) {
		super(msg);
		this.code = code;
	}
	
	public SQLPoolException(int code, Exception ex) {
		super(ex);
		this.code = code;
	}
	
	public int getErrorCode() {
		return code;
	}
	
	private int code;
	
	private static final long serialVersionUID = 1L;
}
