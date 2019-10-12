package xyz.guqing.sort;

import org.junit.Assert;

public class HeapSort {

	// 这个类不应该被实例化。
	private HeapSort() {
	}

	/**
	 * 使用自然顺序按升序重新排列数组。
	 * 
	 * @param array
	 *            需要被排序的数组
	 */
	@SuppressWarnings("rawtypes")
	public static void sort(Comparable[] array) {
		int n = array.length;
		for (int k = n / 2; k >= 1; k--) {
			sink(array, k, n);
		}

		while (n > 1) {
			exchange(array, 1, n--);
			sink(array, 1, n);
		}
	}

	// 元素由上至下的堆有序化(下沉)实现
	@SuppressWarnings("rawtypes")
	private static void sink(Comparable[] array, int k, int n) {
		while (2 * k <= n) {
			int j = 2 * k;
			if (j < n && less(array, j, j + 1)) {
				j++;
			}

			if (!less(array, k, j)) {
				break;
			}

			exchange(array, k, j);
			k = j;
		}
	}

	// 用于比较和交换的辅助函数,用于进行小于比较
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static boolean less(Comparable[] array, int i, int j) {
		// i和j都是从1开始的所以i-1不会小于0
		Assert.assertTrue(i > 0 && j > 0);
		return array[i - 1].compareTo(array[j - 1]) < 0;
	}

	private static void exchange(Object[] array, int i, int j) {
		Object swap = array[i - 1];
		array[i - 1] = array[j - 1];
		array[j - 1] = swap;
	}

	// 打印数组
	@SuppressWarnings("rawtypes")
	private static void show(Comparable[] a) {
		for (int i = 0; i < a.length; i++) {
			System.out.println(a[i]);
		}
	}

	public static void main(String[] args) {
		Integer[] array = { 5, 20, 13, 7, 6, 9, 4 };
		HeapSort.sort(array);
		show(array);
	}
}