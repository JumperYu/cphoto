package com.cp.form;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.junit.Test;

import com.cp.utils.HttpRequestUtil;

public class TestOther {
	
	@Test
	public void testA() throws UnsupportedEncodingException {
		String param = URLEncoder.encode("sign=123","utf-8");
		param += "&transdata={\"appid\":\"3001631470\",\"appuserid\":\"mt_aibei_70820422_1\",\"cporderid\":\"ms_yiliang_70820422_1_1419930077\",\"cpprivate\":\"ms_yiliang70820422_1_1419930075\",\"currency\":\"RMB\",\"feetype\":0,\"money\":1.0,\"paytype\":401,\"result\":0,\"transid\":\"32041412301701181629\",\"transtime\":\"2014-12-30 17:01:42\",\"transtype\":0,\"waresid\":1}";
		param += "&signtype=RSA&a=1";
		HttpRequestUtil.httpRequest("GET", "http://" + "42.62.59.46"
				+ "/ms/aibei_qmfx/payback", param, null);
	}
	
	@Test
	public void testB() {
		System.out.println(Long.toBinaryString(Long.MAX_VALUE).length());
	}
	
	@Test
	public void testDir() {
		File file = new File("d:/123/321/122");
		file.mkdir();
	}
	
	@Test
	public void testInt () {
		System.out.println(Math.pow(2, 8));
	}
	
}
