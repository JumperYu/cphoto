package com.cp.hash;

/**
 * 
 * @author zengxm 2015年3月2日
 * 
 *         坐标类 - 重写hashcode和equals方法让坐标相同的对象使用equals判定的时候相等
 * 
 */
public class Point {

	private int x;
	private int y;

	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * @return the x
	 */
	public int getX() {
		return x;
	}

	/**
	 * @param x
	 *            the x to set
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * @return the y
	 */
	public int getY() {
		return y;
	}

	/**
	 * @param y
	 *            the y to set
	 */
	public void setY(int y) {
		this.y = y;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		/*	
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
		return result;*/
		return HashAlgorithms.additiveHash("" + this.x + this.y, prime);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Point other = (Point) obj;
		if (this.x == other.x && this.y == other.y)
			return true;
		else
			return false;
	}
}
