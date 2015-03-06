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

	/* 一分钟的秒数、一小时的秒数、一天的秒数 */
	public static final long SECONDS_IN_MIN = 60;
	public static final long SECONDS_IN_HOUR = 60 * 60;
	public static final long SECONDS_IN_DAY = 60 * 60 * 24;
	public static final long SECONDS_IN_WEEK = 60 * 60 * 24 * 7;
	
	/* 默认时间格式 */
	public static final String DEFAULT_DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
	
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

	/* 从一个时间点获取前几天后几天 */
	public static Date getDate(long time) {
		return new Date(time);
	}

	public static Date getDate(long time, int dayOffset) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(getDate(time));
		calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DATE)
				+ dayOffset);
		return calendar.getTime();
	}

	// ------------->> 以下获取时间的格式化字符串

	/**
	 * 获取当天的格式化时间
	 * 
	 * @param pattern
	 *            yyyyMMdd HH:mm:ss
	 * @return String
	 */
	public static String getDateStr(String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(new Date());
	}

	/**
	 * 字符串转时间
	 * 
	 * @param time
	 *            时间字符串
	 * @return
	 * @throws ParseException
	 */
	public static Date strToTime(String time) {
		return strToTime(time, DEFAULT_DATETIME_FORMAT);
	}

	/**
	 * 字符串转时间
	 * 
	 * @param time
	 *            时间
	 * @param pattern
	 *            格式
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

	/**
	 * 时间转换为字符串
	 * 
	 * @param date
	 * @return
	 */
	public static String timeToString(Date date) {
		return timeToString(date, DEFAULT_DATETIME_FORMAT);
	}

	/**
	 * 时间转换为字符串
	 * 
	 * @param date
	 * @param pattern
	 *            指定格式
	 * @return
	 */
	public static String timeToString(Date date, String pattern) {
		return new SimpleDateFormat(pattern).format(date);
	}

	/**
	 * 已失效多少天
	 * 
	 * @return
	 */
	public static long expiresIn(long timeseconds) {
		long interval = new Date().getTime() / 1000 - timeseconds;
		return interval / SECONDS_IN_DAY;
	}
}
