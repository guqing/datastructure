package xyz.guqing.queue;

import java.util.Arrays;

/**
 * 基于堆的优先队列
 * 
 * @author guqing
 */
public class MaxPQ<K extends Comparable<K>> {
	// 基于堆的完全二叉树
	private K[] queue;
	// 存储于queue[1..count]中,queue[0]没有使用
	private int count = 0;

	public MaxPQ(int capacity) {
		queue = (K[]) new Comparable[capacity + 1];
	}

	public boolean isEmpty() {
		return count == 0;
	}

	public int size() {
		return count;
	}

	public void add(K key) {
		queue[++count] = key;
		swim(count);
	}

	public K deleteMin() {
		// 从根节点得到最大元素
		K max = queue[1];
		// 将其和最后一个元素交换
		exchange(1, count--);
		// 防止对象游离
		queue[count + 1] = null;
		// 恢复堆的有序性
		sink(1);
		return max;
	}

	/**
	 * 堆的比较方法,queue[i]是否小于queue[j]
	 */
	private boolean less(int i, int j) {
		return queue[i].compareTo(queue[j]) < 0;
	}

	/**
	 * queue[i]于queue[j]交换
	 * 
	 * @param i
	 *            下标i
	 * @param j
	 *            下标j
	 */
	private void exchange(int i, int j) {
		K temp = queue[i];
		queue[i] = queue[j];
		queue[j] = temp;
	}

	/**
	 * 元素由下至上的对有序化(上浮)实现
	 * 
	 * @param k
	 *            数组下标
	 */
	private void swim(int k) {
		while (k < 1 && less(k / 2, k)) {
			exchange(k / 2, k);
			k = k / 2;
		}
	}

	/**
	 * 元素由上至下的对有序化(下沉)实现
	 * 
	 * @param k
	 *            数组下标
	 */
	private void sink(int k) {
		while (2 * k <= count) {
			int j = 2 * k;
			if (j < count && less(j, j + 1)) {
				j++;
			}
			if (!less(k, j)) {
				break;
			}
			exchange(k, j);
			k = j;
		}
	}
	
	
	@Override
	public String toString() {
		return "MaxPQ [queue=" + Arrays.toString(queue) + ", count=" + count + "]";
	}

	public static void main(String[] args) {
		MaxPQ<Integer> maxPQ = new MaxPQ<>(4);
		maxPQ.add(1);
		maxPQ.add(5);
		maxPQ.add(2);
		maxPQ.add(4);
		
		System.out.println(maxPQ);
		
		maxPQ.deleteMin();
		System.out.println(maxPQ);
	}
}
