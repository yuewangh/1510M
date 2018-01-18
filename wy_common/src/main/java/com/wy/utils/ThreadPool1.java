package com.wy.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPool1 {
	static ExecutorService pool = Executors.newFixedThreadPool(3);
	static int[] arr={1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16};
	static int[] arr_new = new int[arr.length];
	static int index=0;
	public static void main(String[] args) {
		while(true){
			System.out.println(index +"<"+ arr_new.length);
			if(index < arr_new.length){
				getParamToArray();
			} else{
				break;
			}
		}
	}

	private static void getParamToArray() {
		pool.execute(new Runnable() {
			public void run() {
				removeArray();
			}
		});
	}
	private static void removeArray(){
		//获取随机数
		int rand =(int)(Math.random()*(arr.length-index-1));
		//把取出的值给新数组
		arr_new[index] = arr[rand];
		//移除数组元素：把最后一个元素替代指定的元素
		arr[rand] = arr[arr.length-index-1];
		arr[arr.length-index-1]=0;
		index++;
	}
}
