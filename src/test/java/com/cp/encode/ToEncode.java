package com.cp.encode;

import org.apache.commons.codec.binary.Base64;
import org.junit.Test;

public class ToEncode {

	@Test
	public void testCode() {
		byte[] driver_bytes = Base64.encodeBase64("com.mysql.jdbc.Driver".getBytes());
		byte[] url_bytes = Base64.encodeBase64("jdbc:mysql://localhost:3306/cms?useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull".getBytes());
		byte[] user_bytes = Base64.encodeBase64("".getBytes());
		byte[] passwd_bytes = Base64.encodeBase64("".getBytes());
		System.out.println(new String(driver_bytes));
		System.out.println(new String(url_bytes));
		System.out.println(new String(user_bytes));
		System.out.println(new String(passwd_bytes));
	}
	
	@Test
	public void testOpts(){
		System.out.println(System.getProperty("code"));
	}
}
