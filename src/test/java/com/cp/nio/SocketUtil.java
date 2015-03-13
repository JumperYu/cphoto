package com.cp.nio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * 工具类
 * 
 * @author zengxm 2015年3月13日
 *
 */
public class SocketUtil {
	
	/**
	 * 字节输出转字符输出
	 * 
	 * @param socket
	 * @return
	 * @throws IOException
	 */
	public static PrintWriter getPrintWriter(Socket socket) throws IOException {
		return new PrintWriter(socket.getOutputStream());
	}
	
	/**
	 * 字节输入转字符输入
	 * 
	 * @param socket
	 * @return
	 * @throws IOException
	 */
	public static BufferedReader getBufferInputStream(Socket socket)
			throws IOException {
		return new BufferedReader(
				new InputStreamReader(socket.getInputStream()));
	}

}
