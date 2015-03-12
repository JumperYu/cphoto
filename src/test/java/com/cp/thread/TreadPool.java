package com.cp.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TreadPool {

	public static void main(String[] args) {
//		ExecutorService pool = Executors.newSingleThreadExecutor();
//		ExecutorService pool = Executors.newFixedThreadPool(2);
		ExecutorService pool = Executors.newCachedThreadPool();

		Thread t1 = new MyThread();

		Thread t2 = new MyThread();

		Thread t3 = new MyThread();

		Thread t4 = new MyThread();

		Thread t5 = new MyThread();

		pool.execute(t1);

		pool.execute(t2);

		pool.execute(t3);

		pool.execute(t4);

		pool.execute(t5);
		// 关闭线程池
		pool.shutdown();
	}
}
