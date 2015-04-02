package com.cp.vm;

import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class References {
	
	private static ReferenceQueue<Obj> rq = new ReferenceQueue<Obj>();

	public static void checkQueue() {
		Reference<? extends Obj> inq = rq.poll();
		if (inq != null) {
			System.out.println("In queue : " + inq.get());
		}
	}

	public static void main(String[] args) {
		final int size = 10;
		List<SoftReference<Obj>> sa = new ArrayList<SoftReference<Obj>>();

		for (int i = 0; i < size; i++) {
			SoftReference<Obj> ref = new SoftReference<Obj>(
					new Obj("Soft " + i), rq);
			System.out.println("Just created: " + ref.get());
			sa.add(ref);
		}

		System.gc();
		checkQueue();
		
		System.out.println(sa.get(5).get());

		List<WeakReference<Obj>> wa = new ArrayList<WeakReference<Obj>>();

		for (int i = 0; i < size; i++) {
			WeakReference<Obj> ref = new WeakReference<Obj>(
					new Obj("Weak " + i), rq);
			System.out.println("Just created: " + ref.get());
			wa.add(ref);
		}

		System.gc();
		checkQueue();
		
		System.out.println(wa.get(5).get());
		
		List<PhantomReference<Obj>> pa = new ArrayList<PhantomReference<Obj>>();

		for (int i = 0; i < size; i++) {
			PhantomReference<Obj> ref = new PhantomReference<Obj>(
					new Obj("Phantom " + i), rq);
			System.out.println("Just created: " + ref.get());
			pa.add(ref);
		}

		System.gc();
		checkQueue();
		
		System.out.println(wa.get(5).get());
	}
}
