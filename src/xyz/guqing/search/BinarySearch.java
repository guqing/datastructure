package xyz.guqing.search;

import java.util.ArrayList;
import java.util.List;

/**
 * 二分查找
 * 
 * @author guqing
 */
public class BinarySearch {
	public static void main(String[] args) {
		int[] array = { 1, 8, 8, 8, 10, 12, 12, 12, 12, 89, 1000, 1221 };
		
		// 非递归方式
		System.out.println("非递归查找key=8: " + search(array, 8));

		// 递归方式的二分查找
		int index = binarySearch(array, 0, array.length - 1, 20);
		System.out.println("非递归查找key=20: " + index);

		System.out.println("查找满足条件的所有元素key=8: " + searchAll(array, 8));
	}

	/**
	 * 非递归方式的二分查找
	 * 
	 * @param array
	 *            源数组
	 * @param key
	 *            待查找的键
	 * @return 找到返回下标，否则返回-1
	 */
	public static int search(int[] array, int key) {
		int low = 0;
		int high = array.length - 1;
		while (low <= high) {
			// 被查找的键要么不存在，要么必然存在于array[low]-array[high]之间
			// mid = (low + high)/2 = low + (high - low)/2
			int mid = low + (high - low) / 2;
			
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

	/**
	 * 递归方式的二分查找
	 * 
	 * @param array
	 *            原数组
	 * @param left
	 *            左边索引
	 * @param right
	 *            右边索引
	 * @param key
	 *            待查找的key值
	 * @return 找到返回下标,否则返回-1
	 */
	public static int binarySearch(int[] array, int left, int right, int key) {
		if (left > right) {
			// 说明没有遍历完整个数组都没有找到，直接返回-1,否则会栈溢出
			return -1;
		}

		// 中值下标
		int mid = (left + right) / 2;
		if (key < array[mid]) {
			// key在mid的左边
			return binarySearch(array, left, mid - 1, key);
		} else if (key > array[mid]) {
			// key在mid的右边
			return binarySearch(array, mid + 1, right, key);
		} else {
			return mid;
		}
	}

	/**
	 * 查找满足值为key的所有下标
	 * 
	 * @param array
	 *            源数组
	 * @param key
	 *            待查找的值
	 * @return 找到返回所有下边的list集合，否则返回null
	 */
	public static List<Integer> searchAll(int[] array, int key) {
		int low = 0;
		int high = array.length - 1;

		List<Integer> result = new ArrayList<>();
		while (low <= high) {
			// 被查找的键要么不存在，要么必然存在于array[low]-array[high]之间
			int mid = low + (high - low) / 2;
			if (key < array[mid]) {
				// key在mid的左边
				high = mid - 1;
			} else if (key > array[mid]) {
				// key在mid的右边
				low = mid + 1;
			} else {
				/**
				 * 1.找到mid后不要直接返回 2.继续向左扫描，将所有满足key的下标加入到集合中
				 * 3.向mid索引值的右边扫描，将所有满足key的下标加入到集合
				 */
				// mid是已经找的值为key的索引
				result.add(mid);

				int temp = mid - 1;
				while (temp > 0 && array[temp] == key) {
					result.add(temp);
					temp--;
				}
				temp = mid + 1;
				while (temp < array.length - 1 && array[temp] == key) {
					result.add(temp);
					temp++;
				}
				return result;
			}
		}

		return null;
	}
}
