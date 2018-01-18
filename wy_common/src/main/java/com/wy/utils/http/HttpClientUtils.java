package com.wy.utils.http;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

public class HttpClientUtils {

	/**
	 * post wangyue 2017年10月24日下午4:16:49
	 * 
	 * @param url
	 * @param params
	 * @param token
	 * @return
	 */
	public static String doPost(String url, Map<String, Object> params,
			String token) {
		String result = null;
		// 创建默认的httpClient实例.
		CloseableHttpClient httpclient = HttpClients.createDefault();
		// 创建httppost
		HttpPost httppost = new HttpPost(url);
		if (token != null && !token.equals("")) {
			httppost.addHeader("Authorization", "Bearer " + token); // 认证token
			httppost.addHeader("Content-Type", "application/json");
			httppost.addHeader("User-Agent", "imgfornote");
		}

		// 创建参数队列
		List<NameValuePair> formparams = new ArrayList<NameValuePair>();
		if (params != null) {
			for (String key : params.keySet()) {
				if (params.get(key) != null) {
					formparams.add(new BasicNameValuePair(key, String
							.valueOf(params.get(key))));
				}
			}
		}
		StringEntity uefEntity;
		try {
			uefEntity = new UrlEncodedFormEntity(formparams, "UTF-8");
			httppost.setEntity(uefEntity);
			CloseableHttpResponse response = httpclient.execute(httppost);
			try {
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					result = EntityUtils.toString(entity, "UTF-8");
				}
			} finally {
				response.close();
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// 关闭连接,释放资源
			try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * get wangyue 2017年10月24日下午4:16:34
	 * 
	 * @param url
	 * @param map
	 * @param token
	 * @return
	 */
	public static String doGet(String url, Map<String, Object> map, String token) {
		String result = null;
		if (map != null) {
			List<NameValuePair> qparams = new ArrayList<NameValuePair>();
			for (Map.Entry<String, Object> entry : map.entrySet()) {
				if (entry.getValue() != null) {
					qparams.add(new BasicNameValuePair(entry.getKey(), String
							.valueOf(entry.getValue())));
				}
			}
			String query = URLEncodedUtils.format(qparams, "UTF-8");
			if (url.indexOf("?") != -1) {
				url += "&" + query;
			} else {
				url += "?" + query;
			}
		}
		// 创建默认的httpClient实例.
		CloseableHttpClient httpclient = HttpClients.createDefault();
		// 创建httpGet
		HttpGet httpGet = new HttpGet(url);
		if (token != null && !token.equals("")) {
			httpGet.addHeader("Authorization", "Bearer " + token); // 认证token
			httpGet.addHeader("Content-Type", "application/json");
			httpGet.addHeader("User-Agent", "imgfornote");
		}
		try {
			CloseableHttpResponse response = httpclient.execute(httpGet);
			try {
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					result = EntityUtils.toString(entity, "UTF-8");
				}
			} finally {
				response.close();
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// 关闭连接,释放资源
			try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
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
}
