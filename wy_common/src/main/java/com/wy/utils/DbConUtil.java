package com.wy.utils;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


public class DbConUtil {
	public static Connection getcon(){
		String propertyPath = "config/jdbc.properties";
		Thread trd= Thread.currentThread();
		ClassLoader cld = trd.getContextClassLoader();
		InputStream inputStream = cld.getResourceAsStream(propertyPath);
		Properties propertie = new Properties();
		try {
			propertie.load(inputStream);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		String driverClassName = ConfigerUtil.getProPerty("jdbc.driverClassName");
		String username = ConfigerUtil.getProPerty("jdbc.username");
		String password = ConfigerUtil.getProPerty("jdbc.password");
		String url = ConfigerUtil.getProPerty("jdbc.url")+"?characterEncoding=utf-8";
		Connection conn = null;
		try {
			Class.forName(driverClassName);
			conn = DriverManager.getConnection(url, username, password);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}
	public static void closeCone(Connection con){
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public static Connection getConnection() {   
		Connection conn = null;   
		String URL = "jdbc:mysql://localhost:3306/yymgc?useUnicode=true&amp;characterEncoding=utf8";   
		String JDBC_DRIVER = "com.mysql.jdbc.Driver";   
		String USER_NAME = "root";   
		String PASSWORD = "123456";
		try {   
			Class.forName(JDBC_DRIVER);   
			conn = DriverManager.getConnection(URL, USER_NAME, PASSWORD);   
		} catch (ClassNotFoundException e) {   
			e.printStackTrace();   
		} catch (SQLException e) {   
			e.printStackTrace();   
		}   
		return conn;   
	}
 
	// 测试
	public static void main(String[] args) throws Exception {
		Connection con = getcon();
		if (con != null) {
			System.out.println("测试成功！");
		}
	}
}
