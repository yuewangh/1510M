package com.wy.utils.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

public class HttpClients {
	/**
	 * 向指定URL发送GET方法的请求
	 * 
	 * @param url
	 *            发送请求的URL
	 * @param param
	 *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @return URL 所代表远程资源的响应结果
	 */
	public static String sendGet(String url, String param) {
		String result = "";
		BufferedReader in = null;
		try {
			String urlNameString = url + "?" + param;
			URL realUrl = new URL(urlNameString);
			// 打开和URL之间的连接
			URLConnection connection = realUrl.openConnection();
			// 设置通用的请求属性
			connection.setRequestProperty("accept", "*/*");
			connection.setRequestProperty("connection", "Keep-Alive");
			connection.setRequestProperty("user-agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 建立实际的连接
			connection.connect();
			// 获取所有响应头字段
			Map<String, List<String>> map = connection.getHeaderFields();
			// 遍历所有的响应头字段
			// for (String key : map.keySet()) {
			// System.out.println(key + "--->" + map.get(key));
			// }
			// 定义 BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			System.out.println("发送GET请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输入流
		finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * 向指定 URL 发送POST方法的请求
	 * 
	 * @param url
	 *            发送请求的 URL
	 * @param param
	 *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @return 所代表远程资源的响应结果
	 */
	public static String sendPost(String url, String param) {
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// 获取URLConnection对象对应的输出流
			out = new PrintWriter(conn.getOutputStream());
			// 发送请求参数
			out.print(param);
			// flush输出流的缓冲
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(
					new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			System.out.println("发送 POST 请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输出流、输入流
		finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * 解析jsonStr
	 * 
	 * @param jsonStr
	 * @return
	 */
	public static Map<String, Object> jsonToMap(String jsonStr) {
		Map<String, Object> map = new HashMap<String, Object>();
		JSONObject jsonObject = new JSONObject(jsonStr);
		Iterator iterator = jsonObject.keys();
		while (iterator.hasNext()) {
			String key = (String) iterator.next();
			Object value = jsonObject.get(key);
			map.put(key, value);
		}
		return map;
	}

	/**
	 * 解析jsonStr
	 * 
	 * @param jsonStr
	 * @return
	 */
	public static List<Map<String, Object>> jsonArrayToMap(String jsonStr) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		JSONArray array = new JSONArray(jsonStr);
		for (int i = 0; i < array.length(); i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			JSONObject iObj = array.getJSONObject(i);
			Iterator iterator = iObj.keys();
			while (iterator.hasNext()) {
				String key = (String) iterator.next();
				Object value = iObj.get(key);
				map.put(key, value);
			}
			list.add(map);
		}
		return list;
	}

	/***
	 * 获取JSON类型 判断规则 判断第一个字母是否为{或[ 如果都不是则不是一个JSON格式的文本
	 * 
	 * @param str
	 * @return
	 */
	public static Boolean iSJsonArray(String str) {
		if (str != null && !str.equals("")) {
			String first = str.substring(0, 1);
			if (first.equals('[')) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 发送待认证post请求
	 */
	public static Map<String, Object> sendTokenPost(String token, String url,
			String param) {
		String rev = "";
		try {
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(url);
			// 添加http头信息
			httppost.addHeader("Authorization", "Bearer " + token); // 认证token
			httppost.addHeader("Content-Type", "application/json");
			httppost.addHeader("User-Agent", "imgfornote");
			httppost.setEntity(new StringEntity(param, "utf-8"));// 加utf-8参数支持中文参数值
			HttpResponse response;
			response = httpclient.execute(httppost);
			// 检验状态码，如果成功接收数据
			int code = response.getStatusLine().getStatusCode();
			if (code == 200) {
				rev = EntityUtils.toString(response.getEntity());//
				System.out.println(rev);
			}
		} catch (Exception e) {
		}
		return jsonToMap(rev);
	}

	/**
	 * 发送待认证的get请求
	 */
	public static Map<String, Object> sendTokenGet(String token, String url) {
		String rev = "";
		try {
			HttpClient httpclient = new DefaultHttpClient();
			HttpGet httpget = new HttpGet(url);
			// 添加http头信息
			httpget.addHeader("Authorization", "Bearer " + token); // 认证token
			HttpResponse response;
			response = httpclient.execute(httpget);
			// 检验状态码，如果成功接收数据
			int code = response.getStatusLine().getStatusCode();
			if (code == 200) {
				rev = EntityUtils.toString(response.getEntity());//
				System.out.println(rev);
			}
		} catch (Exception e) {
		}
		return jsonToMap(rev);
	}

	public static void main(String[] args) {
		String json = sendPost(
				"http://localhost:8081/auth/menu/getListByPid.action", "pid=0");
		System.out.println("keyPost================" + json);
		String json2 = sendGet(
				"http://localhost:8081/auth/menu/getListByPid.action",
				"pid=0d41324d2dcb411d977fa3601c6d80d8");
		System.out.println("keyGet================" + json2);
	}
}
