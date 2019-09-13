package xyz.guqing.sort;

import java.util.Arrays;

public class ShellSort {
	public static void main(String[] args) {
		int[] array = { 8, 9, 1, 7, 2, 3, 5, 4, 6, 0 };
		sortByExchange(array);
		System.out.println(Arrays.toString(array));
	}

	/**
	 * 交换法的希尔排序
	 * @param array 需要排序的数组
	 */
	public static void sortByExchange(int[] array) {
		int temp = 0;
		// 刚开始将数组分为两个一组即分为length/2组，步长gap = length / 2,
		// 每次循环后缩小步长为原来的一半即gap = gap/2，步长等于0即整个数组被当作一个组时就终止循环
		for (int gap = array.length / 2; gap > 0; gap /= 2) {
			for (int i = gap; i < array.length; i++) {
				// 遍历各组中的所有元素(共gap组),步长为gap
				for (int j = i - gap; j >= 0; j -= gap) {
					// 如果当前元素大于加上步长后的那个元素则交换，也就是同一组的两个元素比较
					if (array[j] > array[j + gap]) {
						temp = array[j];
						array[j] = array[j + gap];
						array[j + gap] = temp;
					}
				}
			}
		}
	}

}
