package xyz.guqing.datastructure.sort;

import java.util.Random;

public class MergeSort {
	public static void main(String[] args) {
//		int[] array = { 8, 4, 5, 7, 1, 3, 6, 2 };
//		sort(array);
//		System.out.println(Arrays.toString(array));
		int[] array = new int[80000];
		Random random = new Random();
		for(int i=0; i<80000; i++) {
			array[i] = random.nextInt(80000);
		}
		
		long start = System.currentTimeMillis();
		sort(array);
		long end = System.currentTimeMillis();
		System.out.println("排序所需时间：" + (end - start));
	}

	public static void sort(int[] array) {
		int temp[] = new int[array.length];
		divide(array, 0, array.length - 1, temp);
	}

	/**
	 * 归并排序中的分解方法
	 * 
	 * @param array
	 *            排序的原始数组
	 * @param left
	 *            数组左边初始索引
	 * @param right
	 *            右边索引
	 * @param temp
	 *            临时数组
	 */
	private static void divide(int[] array, int left, int right, int[] temp) {
		if (left < right) {
			// 中间索引
			int mid = (left + right) / 2;
			// 向左递归
			divide(array, left, mid, temp);
			// 向右递归
			divide(array, mid + 1, right, temp);
			// 合并
			merge(array, left, mid, right, temp);
		}
	}

	/**
	 * 归并排序中的合并方法
	 * 
	 * @param array
	 *            排序的原始数组
	 * @param left
	 *            左边的有序序列的初始索引
	 * @param mid
	 *            中间的索引
	 * @param right
	 *            右边索引
	 * @param temp
	 *            临时数组
	 */
	private static void merge(int[] array, int left, int mid, int right, int[] temp) {
		// 初始化low，左边有序序列的初始索引
		int low = left;
		// 初始化high，右边有序序列的初始索引
		int high = mid + 1;
		// 指向temp数组的当前索引
		int t = 0;

		/**
		 * 第一步: 先把左右两边(有序)的数据按照规则填充到temp数组 直到左右两边的有序序列，又一遍处理完毕为止
		 */
		while (low <= mid && high <= right) {
			/**
			 * 如果左边的有序序列的当前元素，小于等于右边有序系列的当前元素 即将左边的当前元素，拷贝到temp数组中 然后t++,low++
			 */
			if (array[low] <= array[high]) {
				temp[t] = array[low];
				t++;
				low++;
			} else {
				// 反之，将右边有序序列的当前元素填充到temp数组
				temp[t] = array[high];
				t++;
				high++;
			}
		}

		// 把有剩余数据的一边的数据依次全部前冲的temp数组
		while (low <= mid) {
			// 左边的有序序列还有剩余的元素，就全部填充到temp
			temp[t] = array[low];
			t++;
			low++;
		}
		while (high <= right) {
			// 右边的有序序列还有剩余的元素，就全部填充到temp
			temp[t] = array[high];
			t++;
			high++;
		}

		// 将temp数组的元素拷贝到array
		t = 0;
		int tempLeft = left;
		while (tempLeft <= right) {
			array[tempLeft] = temp[t];
			t++;
			tempLeft++;
		}
	}
}
