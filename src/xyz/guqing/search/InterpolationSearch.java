package xyz.guqing.search;

/**
 * 插值查找算法
 * @author guqing
 */
public class InterpolationSearch {
	public static void main(String[] args) {
		int[] array = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20 };
		int index = search(array, 3);
		System.out.println(index);
	}
	
	public static int search(int[] array, int key) {
		int low = 0;
		int high = array.length - 1;
		while (low <= high) {
			// 插值查找的mid计算方式
			int mid = low + (high - low)*(key-array[low])/(array[high]-array[low]);
			
			if (key < array[mid]) {
				// key在mid的左边
				high = mid - 1;
			} else if (key > array[mid]) {
				// key在mid的右边
				low = mid + 1;
			} else {
				return mid;
			}
		}
		return -1;
	}
}
