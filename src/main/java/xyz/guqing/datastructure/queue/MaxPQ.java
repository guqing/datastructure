package xyz.guqing.datastructure.queue;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * 基于堆的优先队列
 * 
 * @author guqing
 */
public class MaxPQ<K> implements Iterable<K> {
	// 基于堆的完全二叉树
	private K[] queue;
	// 存储于queue[1..count]中,queue[0]没有使用
	private int count;
	// optional comparator
	private Comparator<K> comparator;

	/**
	 * 使用给定大小初始化一个空的优先队列
	 * 
	 * @param capacity
	 *            初始大小
	 */
	@SuppressWarnings("unchecked")
	public MaxPQ(int initCapacity) {
		queue = (K[]) new Object[initCapacity + 1];
		count = 0;
	}

	/**
	 * 初始化一个空的优先队列
	 */
	public MaxPQ() {
		this(1);
	}

	/**
	 * 用给定的初始容量初始化和给定的比较器创建一个空优先队列
	 * 
	 * @param initCapacity
	 * @param comparator
	 */
	@SuppressWarnings("unchecked")
	public MaxPQ(int initCapacity, Comparator<K> comparator) {
		this.comparator = comparator;
		queue = (K[]) new Object[initCapacity + 1];
		count = 0;
	}

	/**
	 * 使用给定的比较器初始化一个空优先队列
	 * 
	 * @param comparator
	 */
	public MaxPQ(Comparator<K> comparator) {
		this(1, comparator);
	}

	/**
	 * 使用给定的数组初始化优先级队列 使用基于下沉的堆结构，花费的时间与键的数量成比例
	 * 
	 * @param keys
	 */
	@SuppressWarnings("unchecked")
	public MaxPQ(K[] keys) {
		count = keys.length;
		queue = (K[]) new Object[keys.length + 1];
		for (int i = 0; i < count; i++) {
			queue[i + 1] = keys[i];
		}
		for (int k = count / 2; k >= 1; k--) {
			sink(k);
		}
		assert isMaxHeap();
	}

	public boolean isEmpty() {
		return count == 0;
	}

	public int size() {
		return count;
	}

	/**
	 * 返回此优先级队列中的最大值
	 * 
	 * @return
	 */
	public K max() {
		if (isEmpty()) {
			throw new NoSuchElementException("Priority queue underflow");
		}
		return queue[1];
	}

	// 将堆数组扩容至原来的两倍
	@SuppressWarnings("unchecked")
	private void resize(int capacity) {
		assert capacity > count;
		K[] temp = (K[]) new Object[capacity];
		for (int i = 1; i <= count; i++) {
			temp[i] = queue[i];
		}
		queue = temp;
	}

	public void add(K key) {
		// 如果堆数组满,扩容至原来的两倍
		if (count == queue.length - 1) {
			resize(2 * queue.length);
		}
		queue[++count] = key;
		swim(count);
		assert isMaxHeap();
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

	public K deleteMax() {
		if (isEmpty())
			throw new NoSuchElementException("Priority queue underflow");
		K max = queue[1];
		exchange(1, count--);
		sink(1);
		queue[count + 1] = null;
		// 缩容
		if ((count > 0) && (count == (queue.length - 1) / 4)) {
			resize(queue.length / 2);
		}
		assert isMaxHeap();
		return max;
	}

	/**
	 * 堆的比较方法,queue[i]是否小于queue[j] 如果有自定义的比较器就使用自定义的比较器比较
	 */
	@SuppressWarnings("unchecked")
	private boolean less(int i, int j) {
		if (comparator == null) {
			return ((Comparable<K>) queue[i]).compareTo(queue[j]) < 0;
		} else {
			return comparator.compare(queue[i], queue[j]) < 0;
		}
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

	private boolean isMaxHeap() {
		for (int i = 1; i <= count; i++) {
			if (queue[i] == null) {
				return false;
			}
		}

		for (int i = count + 1; i < queue.length; i++) {
			if (queue[i] != null) {
				return false;
			}
		}
		if (queue[0] != null) {
			return false;
		}
		return isMaxHeapOrdered(1);
	}

	private boolean isMaxHeapOrdered(int k) {
		if (k > count) {
			return true;
		}
		int left = 2 * k;
		int right = 2 * k + 1;
		if (left <= count && less(k, left)) {
			return false;
		}
		if (right <= count && less(k, right)) {
			return false;
		}
		return isMaxHeapOrdered(left) && isMaxHeapOrdered(right);
	}

	public Iterator<K> iterator() {
		return new HeapIterator();
	}

	private class HeapIterator implements Iterator<K> {

		//创建一个新的优先队列
		private MaxPQ<K> copy;

		// 将所有的元素添加到copy堆中
		// 需要线性时间,因为已经在堆顺序,所以没有键移动
		public HeapIterator() {
			if (comparator == null) {
				copy = new MaxPQ<K>(size());
			} else {
				copy = new MaxPQ<K>(size(), comparator);
			}

			// 将queue中的数据依次添加到copy副本堆中
			for (int i = 1; i <= count; i++) {
				copy.add(queue[i]);
			}
		}

		public boolean hasNext() {
			return !copy.isEmpty();
		}

		public void remove() {
			throw new UnsupportedOperationException();
		}

		public K next() {
			if (!hasNext()) {
				throw new NoSuchElementException();
			}
			return copy.deleteMax();
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
