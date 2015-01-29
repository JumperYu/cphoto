package com.cp.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 时间工具类
 * 
 * @author zengxm 2014年12月18日
 * 
 */
public class DateUtils {

	public static void main(String[] args) {
		// System.out.println(DateUtils.getStartDate(-1));
		// System.out.println(DateUtils.getEndDate(-1));
		// System.out.println(DateUtils.getDateStr("YYYYMMdd HH:mm:ss"));
		System.out.println(DateUtils.strToTime("2010-01-01 00:00:00"));
	}

	/**
	 * 获取偏移当前天的开始时间 如 2015-01-05 00:00:00
	 * 
	 * @param day
	 *            无符号偏移当天数
	 * @return Date
	 */
	public static Date getStartDate(int day) {
		Calendar todayStart = Calendar.getInstance();
		todayStart.add(Calendar.DATE, day);
		todayStart.set(Calendar.HOUR, 0);
		todayStart.set(Calendar.MINUTE, 0);
		todayStart.set(Calendar.SECOND, 0);
		todayStart.set(Calendar.MILLISECOND, 0);
		return todayStart.getTime();
	}

	/**
	 * 获取偏移当前天的结束时间 如 2015-01-05 23:59:59
	 * 
	 * @param day
	 *            无符号偏移当天数
	 * @return Date
	 */
	public static Date getEndDate(int day) {
		Calendar todayEnd = Calendar.getInstance();
		todayEnd.add(Calendar.DATE, day);
		todayEnd.set(Calendar.HOUR, 23);
		todayEnd.set(Calendar.MINUTE, 59);
		todayEnd.set(Calendar.SECOND, 59);
		todayEnd.set(Calendar.MILLISECOND, 999);
		return todayEnd.getTime();
	}

	// ------------->> 以下获取时间的格式化字符串

	/**
	 * 获取当天的格式化时间
	 * 
	 * @param pattern
	 *            YYYMMdd HH:mm:ss
	 * @return String
	 */
	public static String getDateStr(String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(new Date());
	}

	/**
	 * 字符串转时间
	 * 
	 * @param time 时间字符串
	 * @return
	 * @throws ParseException
	 */
	public static Date strToTime(String time) {
		return strToTime(time, "YYYY-MM-dd HH:mm:ss");
	}

	/**
	 * 字符串转时间
	 * 
	 * @param time	      时间
	 * @param pattern 格式	
	 * @return
	 * @throws ParseException
	 */
	public static Date strToTime(String time, String pattern) {
		Date date = null;
		try {
			date = new SimpleDateFormat(pattern).parse(time);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
}
