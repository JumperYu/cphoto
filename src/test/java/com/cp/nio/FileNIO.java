package com.cp.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * NIO在文件流的应用
 * 
 * ByteBuffer 负责数据包装 FileChannel 负责管道传输
 * 
 * @author zengxm
 *
 */
public class FileNIO {
	
	private static final Charset CHARSET = Charset.forName("utf-8");
	
	public static void main(String[] args) throws IOException {

		final int BYTE_SIZE = 1024;
		FileOutputStream fop = new FileOutputStream("e://1.txt");
		FileChannel fc = fop.getChannel();
		fc.write(ByteBuffer.wrap("中国".getBytes()));
		fc.close();
		fop.close();
		RandomAccessFile raf = new RandomAccessFile("e://1.txt", "rw");
		fc = raf.getChannel();
		fc.position(fc.size());
		fc.write(ByteBuffer.wrap("中文".getBytes()));
		fc.close();
		raf.close();
		FileInputStream fin = new FileInputStream("e://1.txt");
		fc = fin.getChannel();
		ByteBuffer buff = ByteBuffer.allocate(BYTE_SIZE);
		fc.read(buff);
		buff.flip();
		System.out.println(CHARSET.decode(buff));
		for(String s : Files.readAllLines(Paths.get("e://1.txt"), Charset.forName("UTF-8"))){
			System.out.println(s);
		}
		System.out.println("");
		fc.close();		
		fin.close();
		System.out.println("finish");
	}
}
