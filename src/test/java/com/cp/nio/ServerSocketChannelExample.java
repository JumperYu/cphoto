package com.cp.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

public class ServerSocketChannelExample {

	public static void main(String[] args) throws IOException {

		ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
		serverSocketChannel.bind(new InetSocketAddress(8009));
		serverSocketChannel.configureBlocking(false);
		while (true) {
			SocketChannel socketChannel = serverSocketChannel.accept();
			if (socketChannel != null) {
				ByteBuffer buff = ByteBuffer.allocate(24);
				while(socketChannel.read(buff) != -1){
					buff.flip();
					System.out.print("read: " + Charset.forName("utf-8").decode(buff));
					buff.clear();
				}
			}
		}
	}

}
