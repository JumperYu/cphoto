package com.cp.nouse;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

public class TestSoldier {

	// 亦或的数据交换
	@Test
	public void testA() {
		int a = 100;
		int b = 101;
		a = a ^ b;
		b = a ^ b;
		a = a ^ b;
		System.out.println(a);
		System.out.println(b);
	}

	// 加减的数据交换
	@Test
	public void testB() {
		int a = 100;
		int b = 101;
		a = a + b;
		b = a - b;
		a = a - b;
		System.out.println(a);
		System.out.println(b);
	}

	// 排序
	@Test
	public void testC() {
		List<String> arrays = Arrays.asList("张三", "王五", "李四");
		Collections.sort(arrays);
		System.out.println(arrays);
	}
}
