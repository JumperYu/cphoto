package com.cp.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MyExecutor extends Thread {
	private int index;

	public MyExecutor(int i) {
		this.index = i;
	}

	public void run() {
		try {
			System.out.println("[" + this.index + "] start....");
			Thread.sleep((int) (Math.random() * 100));
			System.out.println("[" + this.index + "] end.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String args[]) {
		ExecutorService service = Executors.newSingleThreadExecutor();
		for (int i = 0; i < 10; i++) {
			service.execute(new MyExecutor(i));
			// service.submit(new MyExecutor(i));
		}
		System.out.println("submit finish");
		service.shutdown();
	}
}
