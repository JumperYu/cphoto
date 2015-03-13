package com.cp.nio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * socket
 * 
 * @author zengxm 2015年3月13日
 * 
 */
public class MyServer {

	public static void main(String[] args) {
		
		ServerSocket server = null;
		ExecutorService executorService = Executors.newCachedThreadPool();
		try {
			server = new ServerSocket(8001);
			System.out.println("server start");
			for (;;) {
				final Socket socket = server.accept();
				System.out.println("client:" + socket.getInetAddress().getHostAddress() + " connected");
				executorService.execute(new Thread() {
					@Override
					public void run() {
						BufferedReader in;
						try {
							in = new BufferedReader(new InputStreamReader(
									socket.getInputStream()));
							PrintWriter out = new PrintWriter(socket
									.getOutputStream());
							String line = "";
							while (!(line = in.readLine()).equals("bye")) {
								System.out.println("client:" + line);
								out.println("you said: " + line);
								out.flush();
							} 
							out.write("bye");
							in.close();
							out.close();
							socket.close();
						} catch (IOException e) {
							//e.printStackTrace();
							System.out.println("socket has been closed");
						}
					}
				});
			}
			// server.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
