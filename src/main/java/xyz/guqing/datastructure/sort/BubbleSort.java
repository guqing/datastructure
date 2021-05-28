package xyz.guqing.datastructure.sort;

import java.util.Arrays;

/**
 * 冒泡排序
 * @author guqing
 */
public class BubbleSort {
	public static void main(String[] args) {
		int[] array = {3, 44, 38, 5, 47, 15, 36, 
				26, 27, 2, 46, 4, 19, 50, 48 };
		// 测试排序，时间复杂度为O(n^2),两层for循环
		sort(array);
		System.out.println("排序后：" + Arrays.toString(array));
	}
	
	public static void sort(int[] array) {
		int temp = 0;
		// 标识变量，表示是否进行过交换
		boolean flag = false;
		for(int i = 0; i < array.length - 1; i++) {
			for(int j = 0; j < array.length - 1 - i; j++) {
				if(array[j] > array[j + 1]) {
					// 将交换标记置为true
					flag = true;
					// 左边的数大于右边则交换，大的数逐渐往后靠
					temp = array[j];
					array[j] = array[j + 1];
					array[j + 1] = temp;
				}
			}
			if(!flag) {
				/**
				 * 【优化】在一趟排序中一次都没有交换过,
				 * 无需在交换，节省交换次数次数
				 * 以上使用的array数组15个数据使用了标志
				 * 位只需循环95次，而不使用需要循环105次
				 */
				break;
			} else {
				// 下一趟循环比较时重新置为false
				flag = false;
			}
		}
	}
}
