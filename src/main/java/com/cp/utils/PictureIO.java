package com.cp.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * 文件相关操作
 * 
 * @author zengxm 2015年1月5日
 * 
 */
public class PictureIO {

	private static final Logger log = LoggerFactory.getLogger(PictureIO.class);

	public static void copyFileFromInputStream(InputStream input,
			FileOutputStream output) throws IOException {
		int len = IOUtils.copy(input, output);

		IOUtils.closeQuietly(input);
		IOUtils.closeQuietly(output);

		log.debug(String.format("copy file length:%d finish", len));
	}

	public static void copyFileFromInputStream(InputStream source, String dir,
			String fileName) throws IOException {
		File fileDir = new File(dir);

		if (!fileDir.exists()) {
			fileDir.mkdir();
		}// create dir if not exists

		File destination = new File(fileDir, fileName);

		FileUtils.copyInputStreamToFile(source, destination);

		log.debug(String.format("IO write file %s %s", fileName,
				destination.exists() ? "success" : "fail"));
	}

	/**
	 * 
	 * 复制源文件
	 * 
	 * @param srcFile
	 *            源文件
	 * @param dir
	 *            目标目录
	 * @param fileName
	 *            目标文件名
	 * @throws IOException
	 */
	public static void copyFileFromFile(File srcFile, String dir,
			String fileName) throws IOException {
		File fileDir = new File(dir);

		if (!fileDir.exists()) {
			fileDir.mkdir();
		}// create dir if not exists

		File destination = new File(fileDir, fileName);
		FileUtils.copyFile(srcFile, destination);

		log.debug(String.format("IO write file %s %s", fileName,
				destination.exists() ? "success" : "fail"));
	}

	/**
	 * 
	 * 复制源文件
	 * 
	 * @param srcFile
	 *            源文件
	 * @param filePath
	 *            目标路径
	 * @throws IOException
	 */
	public static void copyFileFromFile(File srcFile, String filePath)
			throws IOException {
		FileUtils.copyFile(srcFile, new File(filePath));
	}
}
