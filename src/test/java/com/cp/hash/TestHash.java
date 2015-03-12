package com.cp.hash;

import java.util.HashSet;

public class TestHash {
	
	public static void main(String[] args) {
		
		Point p1 = new Point(1, 1);
		Point p2 = new Point(2, 1);
		Point p3 = new Point(3, 1);
		Point p4 = new Point(4, 1);
		
		HashSet<Point> points = new HashSet<Point>();
		points.add(p1);
		points.add(p2);
		points.add(p3);
		points.add(p4);
		// 如果这个时候修改p4的值 将导致修改不成功
		//p4.setX(7); 
		//points.remove(p4);
	/*	System.out.println(points.size());
		System.out.println(p1.hashCode());
		System.out.println(p2.hashCode());
		System.out.println(p3.hashCode());
		System.out.println(p4.hashCode());*/
	
		System.out.println(10 >> 3);
		System.out.println(Integer.toBinaryString(225));
		
		int hash, i;
		for(hash = "abc".length(), i = 0; i < "abc".length(); i++){
			hash += "abc".charAt(i);
		}
		System.out.println(hash % 31);
	}
	
}
