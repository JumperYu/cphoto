package com.cp.utils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cp.utils.https.MyHostnameVerifier;
import com.cp.utils.https.MyX509TrustManager;

/**
 * 
 * @author zengxm @date 2014-11-29
 * 
 *         http 请求工具
 * 
 * 
 */
public class HttpRequestUtil {

	private static final Logger log = LoggerFactory
			.getLogger(HttpRequestUtil.class);

	/**
	 * http请求
	 * 
	 * @param method
	 *            GET/POST
	 * @param path
	 *            url
	 * @param content
	 *            body
	 * @param headers
	 *            Map<String, String>
	 * @return String
	 */
	public static String httpRequest(String method, String path,
			String content, Map<String, String> headers) {
		log.debug("request web path:[" + path + "], content:[" + content + "]");
		String ret = "";
		HttpURLConnection conn = null;
		try {
			URL url = new URL(path);
			if (path.startsWith("https")) {// -->> Begin https init
				ignoreSSL();
			}// -->> End of https init
			conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setRequestMethod(method); // set GET/POST
			conn.setUseCaches(false);
			conn.setInstanceFollowRedirects(true);
			conn.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded"); // --> default header
			if (headers != null) {
				for (String key : headers.keySet()) {
					conn.setRequestProperty(key, headers.get(key));
				}// -->> end of for
			}// -->> end of if
			conn.connect();
			if (content != null) {
				DataOutputStream dos = new DataOutputStream(
						conn.getOutputStream());
				// 要传的参数
				dos.writeBytes(content.toString());
				dos.flush();
				dos.close();
			}
			int code = conn.getResponseCode();
			if (code == HttpURLConnection.HTTP_OK) {
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(conn.getInputStream(), "UTF-8"));
				String lines = "";
				while ((lines = reader.readLine()) != null) {
					ret += lines;
				}
				reader.close();
			} else {
				log.error("request error:" + code);
			}
		} catch (Exception e) {
			ret = "";
			log.error(e.getMessage());
		} finally {
			if (conn != null) {
				conn.disconnect();
			}
		}
		log.debug("request web result:[" + ret + "]");
		return ret;
	}

	public static void ignoreSSL() throws NoSuchAlgorithmException,
			KeyManagementException {
		SSLContext sc = null;
		TrustManager[] trustAllCerts = new TrustManager[] { new MyX509TrustManager() };
		sc = SSLContext.getInstance("SSL");
		sc.init(null, trustAllCerts, new SecureRandom());
		HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
		HttpsURLConnection.setDefaultHostnameVerifier(new MyHostnameVerifier());
	}
}
