package com.wy.interceptor;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.wy.utils.CheckUtils;
import com.wy.utils.ErrorCode;
import com.wy.utils.ResultObj;

@Repository
public class SystemInterceptor extends HandlerInterceptorAdapter {
	/*
	 * 
	 * @see
	 * org.springframework.web.servlet.handler.HandlerInterceptorAdapter#preHandle
	 * (javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse, java.lang.Object)
	 */
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {

		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");

		if(!CheckUtils.checkSignature(request)){
			ResultObj ro = new ResultObj();
			ro.setCode(ErrorCode.ERROR2.name);
			PrintWriter out = response.getWriter();
			out.print(JSONObject.fromObject(ro).toString());
			out.close();
			return false;
		}
		return super.preHandle(request, response, handler);
	}
}