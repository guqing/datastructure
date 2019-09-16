package xyz.guqing.search;

import java.util.Arrays;

/**
 * 斐波那契查找
 * 
 * @author guqing
 */
public class FibonacciSearch {
	/**
	 * 斐波那契数列的长度
	 */
	private static final int MAXSIZE = 20;
	
	public static void main(String[] args) {
		int[] array = { 1, 8, 10, 89, 540, 1231 };
		System.out.println(search(array, 10));
	}

	public static int search(int[] array, int key) {
		int low = 0;
		int high = array.length - 1;
		// 斐波那契数列对应的k值
		int k = 0;
		
		// 因为mid = low + F(k-1)-1,所以需要一个斐波那契数列
		int[] fib = getFibonacciArray();
		// 获取斐波那契分割数值的下标
		while (high > fib[k] - 1) {
			k++;
		}
		
		// 因为fib[k]值可能大于数组长度，所以需要使用一个Arrays类构建一个新的数组,类似于数组扩容
		int[] temp = Arrays.copyOf(array, fib[k]);

		// 对temp数组中没有值的部分使用array中最后一个值填充
		for (int i = high + 1; i < temp.length; i++) {
			// 因为high=array.length-1所以array[high]就是最后一个值
			temp[i] = array[high];
		}

		while (low <= high) {
			// 计算mid
			int mid = low + fib[k - 1] - 1;

			// 这里与二分查找不同的地方是使用temp数组
			if (key < temp[mid]) {
				// key在mid的左边
				high = mid - 1;
				/**
				 * 为什么k--
				 * 1.全部元素 = mid之前的元素 + mid之后的元素
				 * 2.fib[k] = fib[k-1] + fib[k-2]
				 * 因为前面由fib[k-1]个元素，所以可以继续拆分fib[k-1] = f[k-2] + f[k-3]
				 * 即在fib[k-1]的前面继续查找，也就是k--
				 */
				k--;
			} else if (key > temp[mid]) {
				// key在mid的右边
				low = mid + 1;
				/**
				 * 为什么是k-2
				 * 1.全部元素 = mid之前的元素 + mid之后的元素
				 * 2.fib[k] = fib[k-1] + fib[k-2] 
				 * 因为后面有fib[k-2]个元素,所以可以继续拆分为fib[k-1] = f[k-3] + f[k-4]
				 * 即在f[k-2]的前面进行查找k-=2 即下次循环mid=f[k-1-2]-1
				 */
				k -= 2;
			} else {
				// 找到后，需要确定返回的是哪个下标
				if (mid <= high) {
					return mid;
				} else {
					return high;
				}
			}
		}
		return -1;
	}

	/**
	 * 非递归方式得到一个斐波那契数列
	 * @param maxSize 数组长度
	 * @return 返回斐波那契数列
	 */
	public static int[] getFibonacciArray() {
		int[] fib = new int[MAXSIZE];
		fib[0] = 1;
		fib[1] = 1;
		for (int i = 2; i < MAXSIZE; i++) {
			fib[i] = fib[i - 1] + fib[i - 2];
		}
		return fib;
	}
}
