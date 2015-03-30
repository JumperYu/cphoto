package com.cp.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * 
 * 一条线程一个选择器处理多个通道
 * 
 * @author zengxm 2015年3月30日
 * 
 */
public class SelectorExample {

	private static ByteBuffer echoBuffer = ByteBuffer.allocate(1024);

	public static void main(String[] args) {
		configure(new int[] { 8008, 8009 });
	}

	public static void configure(int[] ports) {

		try {
			Selector selector = Selector.open();
			for (int port : ports) {
				ServerSocketChannel ssc = ServerSocketChannel.open();
				ssc.configureBlocking(false);
				ssc.bind(new InetSocketAddress(port));
				// ServerSocket ss = ssc.socket();
				ssc.register(selector, SelectionKey.OP_ACCEPT);
			}
			while (true) {
				int readyChannels = selector.select();
				if (readyChannels == 0)
					continue;
				Set<SelectionKey> selectedKeys = selector.selectedKeys();
				Iterator<SelectionKey> it = selectedKeys.iterator();
				while (it.hasNext()) {
					SelectionKey key = it.next();
					if ((key.readyOps() & SelectionKey.OP_ACCEPT) == SelectionKey.OP_ACCEPT) {
						// Accept the new connection
						ServerSocketChannel ssc = (ServerSocketChannel) key
								.channel();
						SocketChannel sc = ssc.accept();
						sc.configureBlocking(false);

						// Add the new connection to the selector
						sc.register(selector,
								SelectionKey.OP_READ);
						it.remove();

						System.out.println("Got connection from " + sc);
					} else if ((key.readyOps() & SelectionKey.OP_READ) == SelectionKey.OP_READ) {
						// Read the data
						SocketChannel sc = (SocketChannel) key.channel();

						// Echo data
						int bytesEchoed = 0;
						while (true) {
							echoBuffer.clear();

							int number_of_bytes = sc.read(echoBuffer);

							if (number_of_bytes <= 0) {
								break;
							}

							echoBuffer.flip();

							sc.write(echoBuffer);
							bytesEchoed += number_of_bytes;
						}

						System.out.println("Echoed " + bytesEchoed + " from "
								+ sc);

						it.remove();
					}

				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
	