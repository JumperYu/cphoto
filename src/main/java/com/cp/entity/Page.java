package com.cp.entity;

/**
 * 分页
 * 
 * @author zengxm
 *
 */
public class Page {

	public static final int DEFAULT_SIZE = 10;

	private int index = 1; // 索引
	private int size = 10; // 页面大小
	private int count = 0; // 页面总数

	public Page() {
	}

	public Page(int index, int size, int count) {
		this.index = index;
		this.size = size;
		this.count = count;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getCount() {
		return count;
	}
	
	public void setCount(int count) {
		this.count = count;
	}
}
