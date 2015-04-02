package com.cp.vm;

public class Obj {
	private static final int SIZE = 10000;
	private double[] d = new double[SIZE];
	private String id;

	public Obj(String id) {
		this.id = id;
	}

	public double[] getD() {
		return d;
	}
	
	public String toString() {
		return "id is : " + id;
	}

	public void finalize() {
		System.out.println("Finalizing ... " + id);
	}
}
