package com.cp.nio;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.Test;

/**
 * NIO在文件流的应用
 * 
 * ByteBuffer负责数据包装 FileChannel负责管道传输
 * 
 * 
 * 
 * @author zengxm
 * 
 */
public class FileNIO {

	private static final Charset CHARSET = Charset.forName("utf-8");

	private static final String PATH = "e://1.sql";

	private static int BYTES_SIZE = 1024 * 1000;

	// 用字节流
	@Test
	public void readFileWithInputStream() {
		try {
			FileInputStream in = new FileInputStream(PATH);
			FileChannel fileChannel = in.getChannel();
			ByteBuffer buff = ByteBuffer.allocate(BYTES_SIZE);
			while (fileChannel.read(buff) != -1) {
				buff.flip();
				System.out.println(CHARSET.decode(buff));
				buff.clear();
			}
			in.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// read file with nio.file
	@Test
	public void readFileWithFiles() {
		try {
			for (String s : Files.readAllLines(Paths.get(PATH), CHARSET)) {
				System.out.println(s);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// read file with random access
	@Test
	public void readFileWithRandomAccess() {
		try {
			RandomAccessFile raf = new RandomAccessFile(PATH, "r");
			FileChannel fc = raf.getChannel();
			ByteBuffer buff = ByteBuffer.allocate(BYTES_SIZE);
			while (fc.read(buff) != -1) {
				buff.flip();
				System.out.println(CHARSET.decode(buff));
				buff.clear();
			}
			raf.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void writeByOutputStream() {

		try {
			FileOutputStream fop = new FileOutputStream(PATH);
			FileChannel fc = fop.getChannel();
			fc.write(ByteBuffer.wrap("中国".getBytes()));
			fop.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
