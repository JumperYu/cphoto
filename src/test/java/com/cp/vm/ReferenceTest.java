package com.cp.vm;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;

public class ReferenceTest {
	
	public static void main(String[] args) {
	}

	@org.junit.Test
	public void hardTest() {
		Obj hard = new Obj("hard");
		hard = null;
		System.gc();
		System.out.println(hard);
	}
	
	@org.junit.Test
	public void softTest() {
		SoftReference<Obj> soft = new SoftReference<Obj>(new Obj("soft"));
		System.gc();
		System.out.println(soft.get());
	}

	@org.junit.Test
	public void weakTest() {
		WeakReference<Obj> weak = new WeakReference<Obj>(new Obj("soft"));
		System.gc();
		System.out.println(weak.get());
	}
	
	@org.junit.Test
	public void phanTest() {
		ReferenceQueue<Obj> rq = new ReferenceQueue<Obj>();
		PhantomReference<Obj> phan = new PhantomReference<Obj>(new Obj("phan"),
				rq);
		// 这里没有调用system.gc();
		System.out.println(phan.get());
	}

}
