package com.wy.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPool {

	 //原数组
	static int[] arr={1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16};
	static int[] arr_new=new int[arr.length];
	static int min=0;
	
	 public static void main(String[] args) { 
		    // 创建一个可重用固定线程数的线程池 ,大小为3
		    ExecutorService pool = Executors.newFixedThreadPool(3); 
		    // 创建线程 
		    Thread t1 = new MyThread(); 
		    Thread t2 = new MyThread(); 
		    Thread t3 = new MyThread();  
		    // 将线程放入池中进行执行 
		    pool.execute(t1); 
		    pool.execute(t2); 
		    pool.execute(t3); 
		    // 关闭线程池 
		    pool.shutdown(); 
	} 
} 
		 
   

class MyThread extends Thread { 
	@Override
	public void run() { 
		
		//随机数
		int m =(int)(Math.random()*ThreadPool.arr.length);
		//把最后一个元素替代指定的元素
		ThreadPool.arr[m-1] = ThreadPool.arr[ThreadPool.arr.length-1];
				 
		
    } 
}