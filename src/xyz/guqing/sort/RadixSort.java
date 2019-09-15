package xyz.guqing.sort;

import java.util.Arrays;

/**
 * 基数排序
 * 
 * @author guqing
 */
public class RadixSort {
	public static void main(String[] args) {
		int[] array = { 53, 3, 542, 748, 214 };
		sort(array);
		System.out.println(Arrays.toString(array));
	}

	public static void sort(int[] array) {
		// 得到数组中的最大位数
		int maxLength = getMaxLegnth(array);

		/**
		 * 定义一个二维数组，表示10个桶，每个桶就是一个一维数组 说明:二维数组包含10个一位数组
		 * 为了防止在放入数的时候，数据溢出则么个一维数组(桶),大小定为array.length 可以使用动态数组改进
		 */
		int[][] bucket = new int[10][array.length];

		// 为了记录每个同种，实际存放了多少个数据，定义一个一维数组来记录各个桶每次放入数据的个数
		int[] bucketSize = new int[10];

		// 进行多少轮与数组中最大数的位数有关，位数是多少就会进行多少轮
		// digit表示数位，循环结束后增加位数，如digit第一次是1，第二次是10，第三次是100
		int digit = 1;
		for (int i = 0; i < maxLength; i++,digit = digit * 10) {
			// 针对每个元素的位进行排序处理
			for (int j = 0; j < array.length; j++) {
				// 依次取出每个元素的每个位，如个位，十位，百位
				int numericalDigit = array[j] / digit % 10;
				// 放入对应的桶中
				bucket[numericalDigit][bucketSize[numericalDigit]] = array[j];
				// 桶中元素数量++
				bucketSize[numericalDigit]++;
			}

			// 按照这个桶的顺序(一维数组的下标一次取出数据，放入原来数组)
			int index = 0;
			// 每遍历一个桶，并将桶中的数据放入到原数组
			for (int k = 0; k < bucketSize.length; k++) {
				// 如果桶中有数据才放入到原始数组
				if (bucketSize[k] != 0) {
					// 循环该桶即第k桶(第k个一位数组)
					for (int l = 0; l < bucketSize[k]; l++) {
						// 取出元素放入到array
						array[index] = bucket[k][l];
						// index++,下一个元素放在下一个位置
						index++;
					}
				}
				// 注意：第i+1轮处理后需要将每个bucketSize[k] = 0，否则会数组越界
				bucketSize[k] = 0;
			}
		}
	}

	private static int getMaxLegnth(int[] array) {
		// 得到数组中最大数的位数
		int max = array[0];
		for (int i = 1; i < array.length; i++) {
			if (array[i] > max) {
				max = array[i];
			}
		}
		// 得到最大位数
		int maxlength = (max + "").length();
		return maxlength;
	}
}
