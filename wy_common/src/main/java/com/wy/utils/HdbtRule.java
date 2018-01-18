package com.wy.utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 活动补贴工具类
 */
public class HdbtRule {
	//记录用户的取票次数
	private static Map<String,Integer> map = new HashMap<String,Integer>();
	//记录取票的用户
	private static List<String> userlist = new ArrayList<String>();
	/**
	 * 
	 *2017年11月1日下午8:48:06
	 * @return 1表示奖励，0表示不奖励
	 */
	public static int js(String id){
		
		
		//1表示奖励，0表示不奖励
		int m =0;
		//获取当前日期
		Calendar now = Calendar.getInstance();  
		int newday = now.get(Calendar.DAY_OF_MONTH);
		//记录当日的所有用户
		if(!userlist.contains(id)){
			userlist.add(id);
		}
		//记录当前用户的取票次数
		Integer count = map.get(id);
		if(count == null){
		   count=1;
			map.put(id, 1);
		} else{
			map.put(id,count+1);
		}
		if(count < 2){
			if(newday >=18 && newday <=20){
				m =1;
			} else if(newday >=21 && newday <=23){
				if(0<userlist.size()%10 && userlist.size()%10<=7){
					m =1;
				}
			} else if(newday >=24 && newday <=27){
				if(0<userlist.size()%20 && userlist.size()%20<=17){
					m =1;
				}
			} else if(newday >=28 && newday <=30){
				if(0<userlist.size()%5 && userlist.size()%5<=4){
					m =1;
				}	
			} 
		}
		return m;
	}
	public static void main(String[] args) {
		for(int i=1;i<=90;i++){
			System.out.println("用户"+i+"第1次取票："+js(i+""));
			System.out.println("用户"+i+"第2次取票："+js(i+""));
			System.out.println("用户"+i+"第3次取票："+js(i+""));
		}
	}
}
