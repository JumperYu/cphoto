package com.cp.nio;

import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class Mem_map_example {
	static final int mem_map_size = 20 * 1024 * 1024;
	static final String mem_file = "e://example_memory_mapped_file.txt";
	static final String disk_file = "e://example_disk_mapped_file.txt";

	public static void main(String[] args) throws Exception {
		createFileByNIO();
		createFileByBIO();
	}

	public static void createFileByNIO() throws Exception {
		RandomAccessFile memoryMappedFile = new RandomAccessFile(mem_file, "rw");

		// Mapping a file into memory
		MappedByteBuffer out = memoryMappedFile.getChannel().map(
				FileChannel.MapMode.READ_WRITE, 0, mem_map_size);

		// Writing into Memory Mapped File
		for (int i = 0; i < mem_map_size; i++) {
			out.put((byte) 'A');
		}
		System.out.println("File '" + mem_file + "' is now "
				+ Integer.toString(mem_map_size) + " bytes full.");

		// Read from memory-mapped file.
		for (int i = 0; i < 30; i++) {
			System.out.print((char) out.get(i));
		}
		memoryMappedFile.close();
		System.out.println("\nReading from memory-mapped file '" + mem_file
				+ "' is complete.");
	}

	public static void createFileByBIO() throws Exception {
		RandomAccessFile raf = new RandomAccessFile(disk_file, "rw");
		for (int i = 0; i < mem_map_size; i++) {
			raf.writeByte('A');
		}
		System.out.println("File '" + disk_file + "' is now "
				+ Integer.toString(mem_map_size) + " bytes full.");
		raf.close();
	}
}
