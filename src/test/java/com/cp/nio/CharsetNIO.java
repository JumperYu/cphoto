package com.cp.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

public class CharsetNIO {
	private Charset charset = Charset.forName("utf-8");
	private SocketChannel channel;

	public void readHtmlContent() {
		try {
			InetSocketAddress address = new InetSocketAddress("www.baidu.com",
					80);
			channel = SocketChannel.open(address);
			channel.write(charset.encode("GET " + "/ HTTP/1.1" + "\r\n\r\n"));
			ByteBuffer buffer = ByteBuffer.allocate(1024);
			while (channel.read(buffer) != -1) {
				buffer.flip();
				System.out.println(charset.decode(buffer));
				buffer.clear();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (channel != null) {
				try {
					channel.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void main(String[] args) {
		new CharsetNIO().readHtmlContent();
	}
}
