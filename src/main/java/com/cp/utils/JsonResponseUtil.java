package com.cp.utils;

public class JsonResponseUtil {
	
	/**
	 * 将固定格式转换为json字符串
	 * 
	 * @param ret constant包下面的常量值
	 * @param msg constant包下面的常量值
	 * @return
	 */
	public static String formate(int ret, String msg){
		return String.format("{\"ret\":%d,\"msg\":%s}", ret, msg);
	}
	
}
