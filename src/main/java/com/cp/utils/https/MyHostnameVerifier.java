package com.cp.utils.https;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

/**
 * 
 * @author zengxm 2014年12月8日
 *
 */
public class MyHostnameVerifier implements HostnameVerifier {

	public boolean verify(String urlHostName, SSLSession session) {
		return urlHostName.equals(session.getPeerHost());
	}
};