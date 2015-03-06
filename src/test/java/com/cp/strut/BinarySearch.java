package com.cp.strut;

/**
 * 
 * 二分查找法的两种实现
 * 
 * @author zengxm 2015年3月6日
 * 
 */
public class BinarySearch {

	public static void main(String[] args) {
		int[] src = new int[] { 5, 4, 3, 2, 1 };
		System.out.println(binarySearch(src, 3));
		System.out.println(binarySearch(src, 3, 0, src.length - 1));
	}

	/**
	 * 二分查找循环查找法
	 * 
	 * @param srcArray
	 *            源数组
	 * @param des
	 *            需要查找的数
	 * @return 返回数组下标 没有则-1
	 */
	public static int binarySearch(int[] srcArray, int des) {
		int low = 0;
		int high = srcArray.length - 1;
		while ((low <= high) && (low <= srcArray.length - 1)
				&& (high <= srcArray.length - 1)) {
			int mid = (high + low) / 2;
			// int middle = low + ((high - low) >> 1);
			System.out.println(mid);
			if (srcArray[mid] == des)
				return mid;
			else if (srcArray[mid] > des) {
				high = mid - 1;
			} else {
				low = mid + 1;
			}
		}
		return -1;
	}

	/**
	 * 二分查找递归算法
	 * 
	 * @param srcArray
	 *            源数组
	 * @param desc
	 *            目标数字
	 * @param begin
	 *            开始坐标
	 * @param end
	 *            结束坐标
	 * @return
	 */
	public static int binarySearch(int[] srcArray, int desc, int begin, int end) {
		if (desc < srcArray[begin] || desc > srcArray[end] || begin > end)
			return -1;

		int mid = (begin + end) / 2;
		System.out.println(mid);
		if (desc == srcArray[mid])
			return mid;
		else if (desc < srcArray[mid])
			return binarySearch(srcArray, desc, begin, mid - 1);
		else
			return binarySearch(srcArray, desc, mid + 1, end);

	}
}