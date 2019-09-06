package xyz.guqing.recursion;

import java.util.Arrays;

/**
 * 回溯法的把皇后问题
 * @author guqing
 */
public class EightQueen {
	/**
	 * 表示共有多少个皇后
	 */
	private static final int MAX = 8;
	private static int[] array = new int[MAX];
	private static int count = 0;
	public static void main(String[] args) {
		// 测试
		play(0);
		System.out.println("八皇后问题解法次数:"+count);
	}
	
	/**
	 * 放置第n个皇后
	 * 注意：play时每一次递归，进入到play中都有一个for循环
	 * 因此会有回溯
	 * @param n
	 */
	public static void play(int n) {
		if(n == MAX) {
			// n=8时，8个皇后已经放好了，下标从0开始
			print();
			count++;
			return;
		}
		// 依次放入皇后，并判断是否冲突
		for(int i=0; i<MAX; i++) {
			// 先把当前的皇后n，放到该行的第1列
			array[n] = i;
			// 判断放置n个皇后到i列时是否冲突
			if(!isConflict(n)){
				// 不冲突，接着放n+1个皇后，开始递归
				play(n + 1);
			}
			// 如果冲突就继续执行array[n] = i;即将第n个皇后放置在本行的后一个位置
		}
	}
	
	/**
	 * 检查放置第n个皇后时该皇后是否和前面已经放置的皇后是否冲突
	 */
	public static boolean isConflict(int n) {
		for(int i=0; i<n; i++) {
			// 第n戈皇后是否和前面的n-1个皇后在 同一列 || 同一斜线
			// 没有必要判断是否在同一行，n每次都在递增，不可能在同一行
			if(array[i] == array[n] || Math.abs(n-i) == Math.abs(array[n] - array[i])) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 打印皇后的摆放位置
	 */
	public static void print() {
		System.out.println(Arrays.toString(array));
	}
}
