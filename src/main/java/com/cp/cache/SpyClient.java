package com.cp.cache;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import net.spy.memcached.MemcachedClient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * Memcached Client Spy
 * 
 * @author zengxm 2014年12月11日
 * 
 */
public class SpyClient {

	private static Logger log = LoggerFactory.getLogger(SpyClient.class);

	private static MemcachedClient mc = null;

	public static final int DEFAULT_SECONDS = 7000;

	public static MemcachedClient getMemcachedClient() {
		if (mc == null) {
			try {
				mc = new MemcachedClient(new InetSocketAddress[] {
						new InetSocketAddress("112.124.126.31", 12000),
						new InetSocketAddress("121.199.67.141", 12000)});
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return mc;
	}

	/**
	 * Spy Memecached Client Set Key-Value
	 * 
	 * @param key
	 * @param value
	 * @param seconds
	 *            DEFAULT_SECONDS
	 * @return
	 */
	public static void set(String key, String value) {

		/* 将key值，过期时间(秒)和要缓存的对象set到memcached中 */
		Future<Boolean> b = null;
		b = getMemcachedClient().set(key, DEFAULT_SECONDS, value);
		try {
			log.debug(b.get() ? String.format("set key:%s=%s success", key,
					value) : String.format("spy client set key:%s=%s fault", key, value));
		} catch (InterruptedException | ExecutionException e) {
			log.error(e.getMessage());
		}
	}
	
	/**
	 * Spy Memecached Client Set Key-Value
	 * 
	 * @param key
	 * @param value
	 * @param seconds
	 *            DEFAULT_SECONDS
	 * @return
	 */
	public static void set(String key, Object value) {

		/* 将key值，过期时间(秒)和要缓存的对象set到memcached中 */
		Future<Boolean> b = null;
		b = getMemcachedClient().set(key, DEFAULT_SECONDS, value);
		try {
			log.debug(b.get() ? String.format("set key:%s=%s success", key,
					value) : String.format("spy client set key:%s=%s fault", key, value));
		} catch (InterruptedException | ExecutionException e) {
			log.error(e.getMessage());
		}
	}

	/**
	 * Spy Memecached Client Set Key-Value
	 * 
	 * @param key
	 * @param value
	 * @param seconds
	 * @return
	 */
	public static boolean set(String key, String value, int seconds) {

		/* 将key值，过期时间(秒)和要缓存的对象set到memcached中 */
		Future<Boolean> b = null;
		b = getMemcachedClient().set(key, seconds, value);
		boolean ret_stat = false;
		try {
			ret_stat = b.get();
			log.debug(String.format("spy client set key:%s=%s %s", key, value,
					ret_stat ? "success" : "fault"));
		} catch (InterruptedException | ExecutionException e) {
			log.error(e.getMessage());
		}

		return ret_stat;
	}

	/**
	 * Spy Memecached Client Get Key
	 * 
	 * @param key
	 * @return
	 */
	public static Object get(String key) {
		Object b = null;
		/* 将key值，过期时间(秒)和要缓存的对象set到memcached中 */
		b = getMemcachedClient().get(key);
		log.debug(String.format("spy client get key:%s=%s", key, b != null ? b.toString()
				: "null"));
		return b;
	}

	public static void main(String[] args) {
		set("test-1", "haha", 60 * 60); // 如果大于30天的秒数会当成unix时间戳 wtf
		System.out.println(get("test-1"));
	}
}
