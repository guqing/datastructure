package xyz.guqing.sort;

import java.util.Random;

public class InsertSort {
	public static void main(String[] args) {
//		int[] array = { 3, 44, 38, 5, 47, 15, 36, 26, 27, 2, 46, 4, 19, 50, 48 };
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
		// 下标从1开始，0位置的数不需要判断而是从第1个位置开始判断与第0个元素比较
		for (int i = 1; i < array.length; i++) {
			// 保存i位置的值,该值是需要寻找插入位置的值
			int insertValue = array[i];
			// 插入位置，首先index=1的元素要与index=1之前的元素比较，以此类推
			int insertIndex = i - 1;

			// 寻找插入位置，需要要从大到小排序变化insertValue > array[insertIndex]即可
			while (insertIndex >= 0 && insertValue < array[insertIndex]) {
				array[insertIndex + 1] = array[insertIndex];
				insertIndex--;
			}
			// 由于insertIndex是后减的所以退出循环后要+1才是找到的插入位置
			array[insertIndex + 1] = insertValue;
		}
	}
}
