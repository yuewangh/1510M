package com.wy.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigerUtil {
	private static String propertyPath = "config/jdbc.properties";

	public static String getProPerty(String name) {
		InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(propertyPath);
		Properties propertie = new Properties();
		try {
			propertie.load(inputStream);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return propertie.getProperty(name);
	}

	public static void main(String[] args) throws Exception {
//		System.out.println(getProPerty("jdbc.username"));
		System.out.println(System.getProperty("user.dir"));
	}
}
