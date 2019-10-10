package xyz.guqing.queue;

import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * 最小优先队列
 * @author guqing
 * @param <K> 泛型K
 */
public class MinPQ<K> implements Iterable<K> {
	 // 在索引1到n处存储元素,即0位置保留不适用
	private K[] queue;
	// 优先队列的元素数量
	private int n;
	// [可选]比较器
	private Comparator<K> comparator;

	/**
	 * 使用给定的初始容量初始化空优先队列
	 *
	 * @param initCapacity
	 *            此优先队列的初始化容量
	 */
	@SuppressWarnings("unchecked")
	public MinPQ(int initCapacity) {
		queue = (K[]) new Object[initCapacity + 1];
		n = 0;
	}

	/**
	 * 初始化一个空的优先队列
	 */
	public MinPQ() {
		this(1);
	}

	/**
	 * 使用给定的初始容量和给定的比较器来初始化空优先队列
	 *
	 * @param initCapacity
	 *            此优先队列的初始容量
	 * @param comparator
	 *            值排序方式的比较器
	 */
	@SuppressWarnings("unchecked")
	public MinPQ(int initCapacity, Comparator<K> comparator) {
		this.comparator = comparator;
		queue = (K[]) new Object[initCapacity + 1];
		n = 0;
	}

	/**
	 * 使用给定的比较器初始化空优先队列
	 *
	 * @param comparator
	 *            值排序方式的比较器
	 */
	public MinPQ(Comparator<K> comparator) {
		this(1, comparator);
	}

	/**
	 * 使用一个数组初始化优先级队列
	 * <p>
	 * 使用基于数组的堆结构，花费的时间与存储数据的数量成比例
	 *
	 * @param keys 源数组
	 */
	@SuppressWarnings("unchecked")
	public MinPQ(K[] keys) {
		n = keys.length;
		queue = (K[]) new Object[keys.length + 1];
		for (int i = 0; i < n; i++) {
			queue[i + 1] = keys[i];
		}
		
		for (int k = n / 2; k >= 1; k--) {
			sink(k);
		}
		assert isMinHeap();
	}

	/**
	 * 如果此优先队列为空，则返回true
	 *
	 * @return 如果优先队列为空返回{@code true}; 否则返回{@code false}
	 */
	public boolean isEmpty() {
		return n == 0;
	}

	/**
	 * 返回此优先级队列上的数据的数量
	 *
	 * @return 优先队列中元素的数量
	 */
	public int size() {
		return n;
	}

	/**
	 * 返回此优先队列上的最小值
	 *
	 * @return 此优先队列上的最小值
	 * @throws NoSuchElementException
	 *             如果队列为空抛出该异常
	 */
	public K min() {
		if (isEmpty()) {
			throw new NoSuchElementException("Priority queue underflow");
		}
		return queue[1];
	}

	// 扩容堆数组的大小的辅助函数,扩容至原来大小的两倍
	@SuppressWarnings("unchecked")
	private void resize(int capacity) {
		assert capacity > n;
		K[] temp = (K[]) new Object[capacity];
		for (int i = 1; i <= n; i++) {
			temp[i] = queue[i];
		}
		queue = temp;
	}

	/**
	 * 向优先队列中添加新的元素
	 *
	 * @param key
	 *            要添加到此优先级队列的数据
	 */
	public void insert(K key) {
		// 如果queue满扩容至原来容量的两倍
		if (n == queue.length - 1) {
			resize(2 * queue.length);
		}

		// 将x添加到堆中并保证堆的平衡性
		queue[++n] = key;
		swim(n);
		assert isMinHeap();
	}

	/**
	 * 删除并返回此优先队列上的最小值
	 *
	 * @return 此优先队列上的最小值
	 * @throws NoSuchElementException
	 *             如果这个优先队列是空的抛出该异常
	 */
	public K delMin() {
		if (isEmpty()) {
			throw new NoSuchElementException("Priority queue underflow");
		}
		K min = queue[1];
		exchange(1, n--);
		sink(1);
		queue[n + 1] = null;
		
		if ((n > 0) && (n == (queue.length - 1) / 4)) {
			resize(queue.length / 2);
		}
		
		assert isMinHeap();
		
		return min;
	}

	
	// 堆数据上浮
	private void swim(int k) {
		while (k > 1 && greater(k / 2, k)) {
			exchange(k, k / 2);
			k = k / 2;
		}
	}

	// 堆数据下沉
	private void sink(int k) {
		while (2 * k <= n) {
			int j = 2 * k;
			if (j < n && greater(j, j + 1))
				j++;
			if (!greater(k, j))
				break;
			exchange(k, j);
			k = j;
		}
	}

	//比较和交换的辅助函数，用于判断i位置元素是否大于j位置
	@SuppressWarnings("unchecked")
	private boolean greater(int i, int j) {
		if (comparator == null) {
			return ((Comparable<K>) queue[i]).compareTo(queue[j]) > 0;
		} else {
			return comparator.compare(queue[i], queue[j]) > 0;
		}
	}

	private void exchange(int i, int j) {
		K swap = queue[i];
		queue[i] = queue[j];
		queue[j] = swap;
	}

	private boolean isMinHeap() {
		return isMinHeap(1);
	}

	// 判断以k为根节点的子树是否是queue[1..n]中的小顶堆
	private boolean isMinHeap(int k) {
		if (k > n) {
			return true;
		}
		int left = 2 * k;
		int right = 2 * k + 1;
		if (left <= n && greater(k, left)) {
			return false;
		}
		if (right <= n && greater(k, right)) {
			return false;
		}
		
		return isMinHeap(left) && isMinHeap(right);
	}

	/**
	 * 返回一个迭代器，该迭代器会按照这个优先队列中值的升序排序迭代数据。
	 * <p>
	 * 迭代器没有实现{@code remove()}方法
	 *
	 * @return 返回一个按升序遍历队列的迭代器
	 */
	public Iterator<K> iterator() {
		return new HeapIterator();
	}

	private class HeapIterator implements Iterator<K> {
		// 创建一个新的队列用于作为副本
		private MinPQ<K> copy;

		// 将queue中的所有数据赋值到copy副本中存储
		// 需要线性时间，因为已经在堆中排序，所以没有数据移动
		public HeapIterator() {
			if (comparator == null) {
				copy = new MinPQ<K>(size());
			} else {
				copy = new MinPQ<K>(size(), comparator);
			}

			for (int i = 1; i <= n; i++) {
				copy.insert(queue[i]);
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
			
			return copy.delMin();
		}
	}

	
	public static void main(String[] args) {
		MinPQ<String> queue = new MinPQ<String>();
		queue.insert("A");
		queue.insert("C");
		queue.insert("D");
		queue.insert("B");
		queue.insert("E");
		
		Iterator<String> it = queue.iterator();
		while(it.hasNext()) {
			System.out.println(it.next());
		}
		
		System.out.println("删除最小值：" + queue.delMin());
	}

}