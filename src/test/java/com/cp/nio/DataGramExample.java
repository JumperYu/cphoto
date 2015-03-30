package com.cp.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class DataGramExample {

	public static void main(String[] args) throws IOException {
	    DatagramChannel channel = DatagramChannel.open();  

	    String newData = "New String to write to file..." + System.currentTimeMillis();  
	      
	    ByteBuffer buf = ByteBuffer.allocate(48);  
	    buf.clear();  
	    buf.put(newData.getBytes());  
	    buf.flip();  
	      
	    int bytesSent = channel.send(buf, new InetSocketAddress("localhost", 8888));  
	    
	    System.out.println(bytesSent);
	}

}
