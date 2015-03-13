package com.cp.nio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MyClient {

	public static void main(String[] args) {
		ExecutorService executorService = Executors.newCachedThreadPool();
		try {
			final Socket socket = new Socket("localhost", 8001);
			System.out.println("client connect to server");
			final BufferedReader in = new BufferedReader(new InputStreamReader(
					System.in));

			final PrintWriter out = SocketUtil.getPrintWriter(socket);
			final BufferedReader is = SocketUtil.getBufferInputStream(socket);

			executorService.execute(new Thread() {
				@Override
				public void run() {
					String line = null;
					try {
						while (!(line = in.readLine()).equals("bye")) {
							out.println(line);
							out.flush();
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			});

			executorService.execute(new Thread() {
				@Override
				public void run() {
					String line = null;
					try {
						while (!(line = is.readLine()).equals("bye")) {
							System.out.println("server:" + line);
						}
						out.close();
						is.close();
						socket.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
