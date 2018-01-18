package com.wy.utils;

public class ResultObj {
	
	private String code=ErrorCode.SUCCESS.name;
	
	private Object obj;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}

}
