package com.cp.nio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
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

	private static final String PATH = "E://test.txt";

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
				System.out.print(CHARSET.decode(buff));
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
				System.out.print(CHARSET.decode(buff));
				buff.clear();
			}
			raf.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// nio write
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

	// 快速生成一个文件
	@Test
	public void writeFileQuick() {
		try {
			RandomAccessFile raf = new RandomAccessFile("E://大于5Mb的文件.txt", "rw");
			FileChannel fc = raf.getChannel();
			int size_Gb = 6; // 生成1G的文件
			for (int g = 0; g < size_Gb; g++) {
				int size_Mb = 1024 * 1024;
				ByteBuffer buffer = ByteBuffer.allocate(size_Mb);
				for (int i = 0; i < size_Mb; i++) {
					buffer.put((byte) 'F');
				}
				buffer.flip();
				fc.write(buffer);
				buffer.clear();
			}
			raf.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}	

	// 使用内存映射快速生成
	@Test
	public void writeFileQuickByMemory() {
		String mem_file = "d://test2.txt";
		int mem_map_size = 1024 * 1024 * 1024;
		try {
			RandomAccessFile raf = new RandomAccessFile(mem_file, "rw");
			MappedByteBuffer buff = raf.getChannel().map(
					FileChannel.MapMode.READ_WRITE, 0, mem_map_size);
			while (buff.position() < mem_map_size) {
				/*
				 * if (buff.position() % 4 == 0) {
				 * buff.put(ByteBuffer.wrap(System.lineSeparator().getBytes()));
				 * continue; }
				 */
				buff.put((byte) 'A');
			}
			raf.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 阻塞IO
	@Test
	public void writeFileByBIO() {
		try {
			FileOutputStream out = new FileOutputStream("d://test3.txt");
			int size = 1024 * 1024 * 1024;
			for (int i = 0; i < size; i++) {
				out.write((byte) 'A');
			}
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// nio copy
	@Test
	public void copyByFileChannel() {
		try {
			RandomAccessFile fromFile = new RandomAccessFile("d://test2.txt",
					"rw");
			FileChannel fromChannel = fromFile.getChannel();

			RandomAccessFile toFile = new RandomAccessFile("d://test4.txt",
					"rw");
			FileChannel toChannel = toFile.getChannel();

			long position = 0;
			long count = fromChannel.size();

			toChannel.transferFrom(fromChannel, position, count);

			fromFile.close();
			toFile.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	// bio copy
	@Test
	public void testBIOCopy() {
		try {
			com.google.common.io.Files.copy(new File("d://test2.txt"), new File("d://test5.txt"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
