package com.cp.web;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import com.google.common.base.Charsets;
import com.google.common.net.HttpHeaders;

/**
 * http工具
 * 
 * @author zengxm 2014-12-20
 * 
 */
public class Servlets {

	private static final Logger log = LoggerFactory.getLogger(Servlets.class);

	/**
	 * 设置让浏览器弹出下载对话框的Header.
	 * 
	 * @param fileName
	 *            下载后的文件名.
	 */
	public static void setFileDownloadHeader(HttpServletResponse response,
			String fileName) {
		// 中文文件名支持
		String encodedfileName = new String(fileName.getBytes(),
				Charsets.ISO_8859_1);
		response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
				"attachment; filename=\"" + encodedfileName + "\"");

	}

	/**
	 * 取得带相同前缀的Request Parameters, copy from spring WebUtils.
	 * 
	 * 返回的结果的Parameter名已去除前缀.
	 */
	public static Map<String, Object> getParametersStartingWith(
			ServletRequest request, String prefix) {
		Enumeration<String> paramNames = request.getParameterNames();
		Map<String, Object> params = new TreeMap<String, Object>();
		if (prefix == null) {
			prefix = "";
		}
		while ((paramNames != null) && paramNames.hasMoreElements()) {
			String paramName = (String) paramNames.nextElement();
			if ("".equals(prefix) || paramName.startsWith(prefix)) {
				String unprefixed = paramName.substring(prefix.length());
				String[] values = request.getParameterValues(paramName);
				if ((values == null) || (values.length == 0)) {
					// Do nothing, no values found at all.
				} else if (values.length > 1) {
					params.put(unprefixed, values);
				} else {
					params.put(unprefixed, values[0]);
				}
			}
		}
		return params;
	}

	/**
	 * 组合Parameters生成Query String的Parameter部分, 并在paramter name上加上prefix.
	 * 
	 * @see #getParametersStartingWith
	 */
	public static String encodeParameterStringWithPrefix(
			Map<String, Object> params, String prefix) {
		if (CollectionUtils.isEmpty(params)) {
			return "";
		}

		if (prefix == null) {
			prefix = "";
		}

		StringBuilder queryStringBuilder = new StringBuilder();
		Iterator<Entry<String, Object>> it = params.entrySet().iterator();
		while (it.hasNext()) {
			Entry<String, Object> entry = it.next();
			queryStringBuilder.append(prefix).append(entry.getKey())
					.append('=').append(entry.getValue());
			if (it.hasNext()) {
				queryStringBuilder.append('&');
			}
		}
		return queryStringBuilder.toString();
	}

	/**
	 * 打印请求头信息
	 * 
	 * @param request
	 */
	public static void printHeaderWithHttpRequest(HttpServletRequest request) {
		Enumeration<String> headers = request.getHeaderNames();
		Map<String, Object> headerMap = new HashMap<String, Object>();
		while (headers.hasMoreElements()) {
			String key = headers.nextElement();
			headerMap.put(key, request.getHeader(key));
		}// -->> print headers
		log.debug(String.format("request %s [header]: %s",
				request.getRequestURI(), headerMap.toString()));
	}

	/**
	 * 打印请求头信息并转成Map返回
	 * 
	 * @param request
	 */
	public static Map<String, Object> getHeaderWihtHttpRequest(
			HttpServletRequest request) {
		Enumeration<String> headers = request.getHeaderNames();
		Map<String, Object> headerMap = new HashMap<String, Object>();
		while (headers.hasMoreElements()) {
			String key = headers.nextElement();
			headerMap.put(key, request.getHeader(key));
		}
		// -->> print headers
		log.debug(String.format("request %s [header]: %s",
				request.getRequestURI(), headerMap.toString()));
		return headerMap;
	}

	public static void printBodyWihtHttpRequest(HttpServletRequest request) {
		Enumeration<String> paramNames = request.getParameterNames();
		Map<String, Object> params = new TreeMap<String, Object>();
		while (paramNames.hasMoreElements()) {
			String paramName = paramNames.nextElement();
			String[] values = request.getParameterValues(paramName);
			if ((values == null) || (values.length == 0)) {
				// Do nothing, no values found at all.
			} else if (values.length > 1) {
				params.put(paramName, values);
			} else {
				params.put(paramName, values[0]);
			}
		}
		log.debug(String.format("request %s [body]: %s",
				request.getRequestURI(), params.toString()));
		// return params;
	}

	public static Map<String, Object> getBodyWihtHttpRequest(
			HttpServletRequest request) {
		Enumeration<String> paramNames = request.getParameterNames();
		Map<String, Object> params = new TreeMap<String, Object>();
		while (paramNames.hasMoreElements()) {
			String paramName = paramNames.nextElement();
			String[] values = request.getParameterValues(paramName);
			if ((values == null) || (values.length == 0)) {
				// Do nothing, no values found at all.
			} else if (values.length > 1) {
				params.put(paramName, values);
			} else {
				params.put(paramName, values[0]);
			}
		}
		log.debug(String.format("request %s [body]: %s",
				request.getRequestURI(), params.toString()));
		return params;
	}

	public static String ignoreStringNull(Object var) {
		if (var != null && var instanceof String)
			return (String) var;
		else {
			log.error("calling ignoreStringNull function error, beacause object is not a string.");
			return "";
		}
	}
}
