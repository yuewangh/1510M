package com.wy.utils;


/**
 * 业务类型
 */
public enum LogType {
	SEXMAN("1","男"),
	SEXWOMAN("2","女");
	public String name;
	public String value;

	LogType(String name, String value) {
		this.value = value;
		this.name = name;
	}
	public static void main(String[] args) {
		System.out.println(LogType.SEXMAN.name);
		System.out.println(LogType.SEXMAN.value);
		LogType[] values = LogType.values();
		for (int i = 0; i < values.length; i++) {
			System.out.println(values[i].name);
			System.out.println(values[i].value);
		}
	}
}
