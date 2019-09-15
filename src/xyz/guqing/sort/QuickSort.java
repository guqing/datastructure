package xyz.guqing.sort;

import java.util.Random;

public class QuickSort {
	public static void main(String[] args) {
//		int[] array = { 3, 44, 38, 5, 47, 15, 36, 26, 27, 2, 46, 4, 19, 50, 48 };
//		sort(array, 0, array.length - 1);
//		System.out.println(Arrays.toString(array));
		
		int[] array = new int[80000];
		Random random = new Random();
		for(int i=0; i<80000; i++) {
			array[i] = random.nextInt(80000);
		}
		
		long start = System.currentTimeMillis();
		sort(array, 0, array.length - 1);
		long end = System.currentTimeMillis();
		System.out.println("排序所需时间：" + (end - start));
	}

	public static void sort(int[] array, int left, int right) {
		if (left > right) {
			return;
		}
		int temp = 0;
		int low = left;
		int high = right;
		// pivot就是基准位
		int pivot = array[left];

		// while循环的目的是让比pivot的值小的放到左边
		// while循环的目的是让比pivot的值大的放到右边
		while (low < high) {
			// 先看右边，依次往左递减
			while (pivot <= array[high] && low < high) {
				high--;
			}
			// 再看左边，依次往右递增
			while (pivot >= array[low] && low < high) {
				low++;
			}
			// 如果满足条件则交换
			if (low < high) {
				temp = array[high];
				array[high] = array[low];
				array[low] = temp;
			}

		}
		// 最后将基准为与low和high相等位置的数字交换
		array[left] = array[low];
		array[low] = pivot;
		// 递归调用左半数组
		sort(array, left, high - 1);
		// 递归调用右半数组
		sort(array, high + 1, right);
	}
}
