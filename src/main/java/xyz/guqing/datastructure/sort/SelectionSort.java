package xyz.guqing.datastructure.sort;

import java.util.Random;

/**
 * 选择排序
 * @author guqing
 */
public class SelectionSort {
	public static void main(String[] args) {
//		int[] array = {3, 44, 38, 5, 47, 15, 36, 
//				26, 27, 2, 46, 4, 19, 50, 48 };
//		sort(array);
//		System.out.println(Arrays.toString(array));
		
		int[] array = new int[80000];
		Random random = new Random();
		for(int i=0; i<80000; i++) {
			array[i] = random.nextInt(80000);
		}
		
		long start = System.currentTimeMillis();
		// 时间复杂度为O(n^2),两层for循环
		sort(array);
		long end = System.currentTimeMillis();
		System.out.println("排序所需时间：" + (end - start));
	}
	
	/**
	 * 选择排序主要的思想就是:从第一个位置开始，假设它就是从它之后数最小的
	 * 然后遍历开始判断是不是真的是最小的，如果不是那就交换它和真正的最小值的位置
	 * 这样遍历下来每一次遍历就能排序好一个数，下一次循环就从上一次假定的最小值的
	 * 下一个值开始判断找到真正的最小值与之交换
	 * 注意：这里说的最小值都是从假定是最小值的那个位置往后找到所谓的最小值，因为
	 * 假定是最小值的位置之前的数都是排好序的不需要考虑。
	 * @param array
	 */
	public static void sort(int[] array) {
		for(int i = 0; i < array.length - 1; i++) {
			int minIndex = i;
			int min = array[i];
			for(int j = i + 1; j < array.length; j++) {
				if(min > array[j]) {
					// 假设的最小值不是真正的最小
					// 重置min
					min = array[j];
					// 重置minIndex
					minIndex = j;
				}
			}
			
			// 将最小值，放在假定的最小值的位置上，而原假定位置的值放在真正的最小值的位置
			if(minIndex != i) {
				// 即交换假定的最小值所在位置和真正寻找到的最小值位置上的值
				array[minIndex] = array[i];
				array[i] = min;
			}
		}
	}
}
