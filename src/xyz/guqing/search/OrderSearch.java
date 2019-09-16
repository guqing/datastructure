package xyz.guqing.search;
/**
 * 顺序查找
 * @author guqing
 */
public class OrderSearch {
	public static void main(String[] args) {
		int[] array = {1,9,11,-1,34,89};
		int index = search(array, 11);
		if(index == -1) {
			System.out.println("没有查找到对应值");
		} else {
			System.out.println("找到该值，下标为:" + index);
		}
	}
	/**
	 * @param array 待查找的数组
	 * @param value 需要查找的值
	 * @return 查找到一个满足条件值就返回该值对应的下标，查找结束，如果没有找到返回-1
	 */
	public static int search(int[] array, int value) {
		for(int i=0; i<array.length; i++) {
			if(array[i] == value) {
				return i;
			}
		}
		return -1;
	}
}
