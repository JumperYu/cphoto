package com.cp.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;

import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;

/**
 * 
 * @author ZengXM created at 2014-04-14
 * 
 *         JavaCSV 2.0
 * 
 */
public class CSVUtil {

	private static CsvReader reader;				
	private static CsvWriter writer;
	
	
	private static final char DEFAULT_LIMITER = ',';			// 分隔符
	private static final String DEFAULT_CHARSET = "GB2312";		// 字符编码

	/**
	 * 创建输入流
	 * 
	 * @param inputStream
	 * @return
	 */
	public static CsvReader createReader(InputStream inputStream) {
		reader = new CsvReader(inputStream, DEFAULT_LIMITER,
				Charset.forName(DEFAULT_CHARSET));
		return reader;
	}

	/**
	 * 创建输入流
	 * 
	 * @param inputStream
	 * @param delimiter
	 * @param charset
	 * @return
	 */
	public static CsvReader createReader(InputStream inputStream,
			char delimiter, String charset) {
		reader = new CsvReader(inputStream, delimiter, Charset.forName(charset));
		return reader;
	}

	/**
	 * 创建输出流
	 * 
	 * @param out
	 * @param charset
	 * @return
	 */
	public static CsvWriter createWriter(OutputStream out) {
		writer = new CsvWriter(out, DEFAULT_LIMITER,
				Charset.forName(DEFAULT_CHARSET));
		return writer;
	}

	/**
	 * 创建输出流
	 * 
	 * @param out
	 * @param delimiter
	 * @param charset
	 * @return
	 */
	public static CsvWriter createWriter(OutputStream out, char delimiter,
			String charset) {
		writer = new CsvWriter(out, delimiter, Charset.forName(charset));
		return writer;
	}
	
	public static void writeLine(Object[] values){
		for(Object v : values){
			writeCell(v.toString());
		}
		writeEnd();
	}
	
	public static void writeLine(String[] values){
		try {
			writer.writeRecord(values);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void writeLine(String value){
		try {
			writer.write(value);
			writer.endRecord();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void writeCell(String value){
		try {
			writer.write(value);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void writeEnd(){
		try {
			writer.endRecord();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void closeWriter(){
		if(writer != null)
			writer.close();
	}
	
}
