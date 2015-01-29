package com.cp.encode;

import org.apache.commons.codec.binary.Base64;
import org.junit.Test;

public class ToEncode {

	@Test
	public void testCode() {
		byte[] bytes = Base64.encodeBase64("com.mysql.jdbc.Driver".getBytes());
		String encodeStr = new String(bytes);
		System.out.println(encodeStr);
	}

}
