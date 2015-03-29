package com.cp.thread;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import com.cp.utils.DateUtils;

public class SynObject {

	public static void insert(int i) throws IOException {
		PrintWriter out = new PrintWriter(new FileOutputStream("e://db.txt",
				true));
		if (!hasIt(i)) {
			out.println(i);
		}
		out.close();
	}

	public synchronized static boolean hasIt(int i) throws IOException {
		System.out.println(DateUtils.timeToString(new Date()) + "," + i);
		BufferedReader in = new BufferedReader(new InputStreamReader(
				new FileInputStream("e://db.txt")));
		String line = null;
		while ((line = in.readLine()) != null) {
			if (Integer.parseInt(line) == i) {
				in.close();
				return true;
			}
		}
		in.close();
		return false;
	}

	public static void main(String[] args) {

		ExecutorService service = Executors.newFixedThreadPool(5);
		service.execute(new MyThread());
		service.execute(new MyThread());
		service.execute(new MyThread());
		service.execute(new MyThread());
		service.execute(new MyThread());
		try {
			TimeUnit.MILLISECONDS.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	static class MyThread extends Thread {
		AtomicInteger atomicInteger = new AtomicInteger(1);

		@Override
		public void run() {
			while (true) {
				try {
					insert(atomicInteger.getAndIncrement());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
