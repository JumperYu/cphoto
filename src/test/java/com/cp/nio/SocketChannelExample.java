package com.cp.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class SocketChannelExample {

	public static void main(String[] args) throws IOException {
		SocketChannel socketChannel = SocketChannel.open();
		//socketChannel.configureBlocking(false);
		socketChannel.connect(new InetSocketAddress("localhost", 8001));
		socketChannel.write(ByteBuffer.wrap("中文".getBytes()));
		//socketChannel.close();
	}

}
