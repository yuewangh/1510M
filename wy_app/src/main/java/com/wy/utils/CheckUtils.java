package com.wy.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

public class CheckUtils {
	/**
	 * 验证签名
	 * @param request
	 * @return
	 */
	public static boolean checkSignature(HttpServletRequest request) {

		String signature = request.getHeader("signature");
		
		Map<String,String> params = new HashMap<String,String>();
		Map<String,String[]> requestParams = request.getParameterMap();
		for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i]: valueStr + values[i] + ",";
			}
			params.put(name, valueStr);
		}
		Boolean res= false;
		try {
			String sign = createSign(params);
			if(sign.equals(signature)){
				res=true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}

	/**
	 * 生成签名
	 * 
	 * @param params
	 * @param encode
	 * @return
	 * @throws Exception
	 */
	public static String createSign(Map<String, String> params){
		//把所有参数排序
		Set<String> keysSet = params.keySet();
		Object[] keys = keysSet.toArray();
		Arrays.sort(keys);
		
		//把所有参数拼接成字符串
		StringBuffer temp = new StringBuffer();
		boolean first = true;
		for (Object key : keys) {
			if (first) {
				first = false;
			} else {
				temp.append("&");
			}
			temp.append(key).append("=");
			Object value = params.get(key);
			String valueString = "";
			if (null != value) {
				valueString = String.valueOf(value);
			}
			try {
				temp.append(URLEncoder.encode(valueString, "UTF-8"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		//把拼接成的字符串Md5加密，生成签名
		return MD5Utils.encode(temp.toString()).toUpperCase();
	}
}
