package com.wy.utils;


/**
 * 业务类型
 */
public enum ErrorCode {
	SUCCESS("00000","成功"),
	ERROR1("00001","运行错误"),
	ERROR2("00002","签名错误"),
	ERROR3("00003","参数错误");
	
	public String name;
	public String value;

	ErrorCode(String name, String value) {
		this.value = value;
		this.name = name;
	}
}
