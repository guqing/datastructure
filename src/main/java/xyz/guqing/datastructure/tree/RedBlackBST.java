package xyz.guqing.datastructure.tree;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Stack;

/**
 * 红黑树
 * 
 * @author guqin
 * @param <K>
 *            key
 * @param <V>
 *            value
 */
public class RedBlackBST<K extends Comparable<K>, V> {
	private Node<K, V> root;
	private static final boolean RED = true;
	private static final boolean BLACK = false;

	private static class Node<K, V> {
		K key;
		V value;
		Node<K, V> left;
		Node<K, V> right;
		// 这棵树中的节点总数
		int count;
		// 由其父节点指向它的链接的颜色
		boolean color;

		public Node(K key, V value, int count, boolean color) {
			super();
			this.key = key;
			this.value = value;
			this.count = count;
			this.color = color;
		}
	}

	private boolean isRed(Node<K, V> node) {
		if (node == null) {
			return false;
		}
		return node.color == RED;
	}

	public int size() {
		return size(root);
	}

	private int size(Node<K, V> node) {
		if (node == null) {
			return 0;
		}
		return node.count;
	}

	private Node<K, V> rotateLeft(Node<K, V> h) {
		Node<K, V> x = h.right;
		h.right = x.left;
		x.left = h;

		x.color = h.color;
		h.color = RED;
		x.count = h.count;
		h.count = 1 + size(h.left) + size(h.right);
		return x;
	}

	private Node<K, V> rotateRight(Node<K, V> h) {
		Node<K, V> x = h.left;
		h.left = x.right;
		x.right = h;

		x.color = h.color;
		h.color = RED;
		x.count = h.count;
		h.count = 1 + size(h.left) + size(h.right);
		return x;
	}

	private void flipColors(Node<K, V> h) {
		// h must have opposite color of its two children
		// assert (h != null) && (h.left != null) && (h.right != null);
		// assert (!isRed(h) && isRed(h.left) && isRed(h.right))
		// || (isRed(h) && !isRed(h.left) && !isRed(h.right));
		h.color = !h.color;
		h.left.color = !h.left.color;
		h.right.color = !h.right.color;
	}

	public void put(K key, V val) {
		if (key == null)
			throw new IllegalArgumentException("first argument to put() is null");
		if (val == null) {
			delete(key);
			return;
		}

		root = put(root, key, val);
		root.color = BLACK;
		// assert check();
	}

	// insert the key-value pair in the subtree rooted at h
	private Node<K, V> put(Node<K, V> h, K key, V val) {
		if (h == null) {
			return new Node<K, V>(key, val, 1, RED);
		}

		int cmp = key.compareTo(h.key);
		if (cmp < 0) {
			h.left = put(h.left, key, val);
		} else if (cmp > 0) {
			h.right = put(h.right, key, val);
		} else {
			h.value = val;
		}

		// fix-up any right-leaning links
		if (isRed(h.right) && !isRed(h.left)) {
			h = rotateLeft(h);
		}
		if (isRed(h.left) && isRed(h.left.left)) {
			h = rotateRight(h);
		}
		if (isRed(h.left) && isRed(h.right)) {
			flipColors(h);
		}

		h.count = size(h.left) + size(h.right) + 1;

		return h;
	}

	public V get(K key) {
		return get(root, key);
	}

	/**
	 * 在以parent为根节点的子树中查找并返回key所对应的值
	 * 
	 * @param parent
	 *            父节点
	 * @param key
	 *            查找的key
	 * @return 找到返回value,否则返回null
	 */
	private V get(Node<K, V> parent, K key) {
		if (parent == null) {
			return null;
		}
		int cmp = key.compareTo(parent.key);
		if (cmp < 0) {
			// 查找左子树
			return get(parent.left, key);
		} else if (cmp > 0) {
			// 查找右子树
			return get(parent.right, key);
		} else {
			return parent.value;
		}
	}
	
	public boolean contains(K key) {
		return get(key) != null;
	}

	public boolean isEmpty() {
		return root == null;
	}
	
	public K min() {
		return min(root).key;
	}

	/**
	 * 获取最小键,如果根节点的左链接为空，那么一棵二叉查找树 中最小的键就是根节点，如果左链接非空，那么树中最小键 就是左子树中的最小键
	 * 
	 * @return
	 */
	private Node<K, V> min(Node<K, V> parent) {
		if (parent.left == null) {
			return parent;
		}
		return min(parent.left);
	}

	public K max() {
		return max(root).key;
	}

	/**
	 * 只是在min()的基础上将left和right调换 并将> 和 < 调换即可
	 * 
	 * @return 返回最大节点
	 */
	private Node<K, V> max(Node<K, V> parent) {
		if (parent.right == null) {
			return parent;
		}
		return max(parent.right);
	}

	private Node<K, V> balance(Node<K, V> h) {
		// assert (h != null);
		if (isRed(h.right)) {
			h = rotateLeft(h);
		}
		if (isRed(h.left) && isRed(h.left.left)) {
			h = rotateRight(h);
		}
		if (isRed(h.left) && isRed(h.right)) {
			flipColors(h);
		}

		h.count = size(h.left) + size(h.right) + 1;
		return h;
	}

	// Assuming that h is red and both h.left and h.left.left
	// are black, make h.left or one of its children red.
	private Node<K, V> moveRedLeft(Node<K, V> h) {
		// assert (h != null);
		// assert isRed(h) && !isRed(h.left) && !isRed(h.left.left);

		flipColors(h);
		if (isRed(h.right.left)) {
			h.right = rotateRight(h.right);
			h = rotateLeft(h);
			flipColors(h);
		}
		return h;
	}

	// Assuming that h is red and both h.right and h.right.left
	// are black, make h.right or one of its children red.
	private Node<K, V> moveRedRight(Node<K, V> h) {
		// assert (h != null);
		// assert isRed(h) && !isRed(h.right) && !isRed(h.right.left);
		flipColors(h);
		if (isRed(h.left.left)) {
			h = rotateRight(h);
			flipColors(h);
		}
		return h;
	}

	public void delete(K key) {
		if (key == null) {
			throw new IllegalArgumentException("argument to delete() is null");
		}

		if (!contains(key)) {
			return;
		}

		// if both children of root are black, set root to red
		if (!isRed(root.left) && !isRed(root.right)) {
			root.color = RED;
		}

		root = delete(root, key);

		if (!isEmpty()) {
			root.color = BLACK;
		}
		// assert check();
	}

	// delete the key-value pair with the given key rooted at h
	private Node<K, V> delete(Node<K, V> h, K key) {
		// assert get(h, key) != null;

		if (key.compareTo(h.key) < 0) {
			if (!isRed(h.left) && !isRed(h.left.left))
				h = moveRedLeft(h);
			h.left = delete(h.left, key);
		} else {
			if (isRed(h.left))
				h = rotateRight(h);
			if (key.compareTo(h.key) == 0 && (h.right == null))
				return null;
			if (!isRed(h.right) && !isRed(h.right.left))
				h = moveRedRight(h);
			if (key.compareTo(h.key) == 0) {
				Node<K, V> x = min(h.right);
				h.key = x.key;
				h.value = x.value;
				// h.val = get(h.right, min(h.right).key);
				// h.key = min(h.right).key;
				h.right = deleteMin(h.right);
			} else
				h.right = delete(h.right, key);
		}
		return balance(h);
	}
	
	/**
	 * 删除最小键
	 */
	public void deleteMin() {
		if (isEmpty()) {
			throw new NoSuchElementException("BST underflow");
		}

		// if both children of root are black, set root to red
		if (!isRed(root.left) && !isRed(root.right))
			root.color = RED;

		root = deleteMin(root);
		if (!isEmpty())
			root.color = BLACK;
		// assert check();
	}

	// delete the key-value pair with the minimum key rooted at h
	private Node<K, V> deleteMin(Node<K, V> h) {
		if (h.left == null)
			return null;

		if (!isRed(h.left) && !isRed(h.left.left))
			h = moveRedLeft(h);

		h.left = deleteMin(h.left);
		return balance(h);
	}

	/**
	 * 删除最大键
	 */
	public void deleteMax() {
		if (isEmpty()) {
			throw new NoSuchElementException("BST underflow");
		}

		// if both children of root are black, set root to red
		if (!isRed(root.left) && !isRed(root.right))
			root.color = RED;

		root = deleteMax(root);
		if (!isEmpty())
			root.color = BLACK;
		// assert check();
	}

	// delete the key-value pair with the maximum key rooted at h
	private Node<K, V> deleteMax(Node<K, V> h) {
		if (isRed(h.left))
			h = rotateRight(h);

		if (h.right == null)
			return null;

		if (!isRed(h.right) && !isRed(h.right.left))
			h = moveRedRight(h);

		h.right = deleteMax(h.right);

		return balance(h);
	}

	/**
	 * @return 返回所有的key
	 */
	public Iterable<K> keys() {
		return keys(min(), max());
	}

	/**
	 * 范围查找
	 * 
	 * @param low
	 * @param high
	 * @return
	 */
	public Iterable<K> keys(K low, K high) {
		List<K> list = new ArrayList<>();
		keys(root, list, low, high);
		return list;
	}

	private void keys(Node<K, V> parent, List<K> list, K low, K high) {
		if (parent == null) {
			return;
		}

		int cmpLow = low.compareTo(parent.key);
		int cmpHigh = high.compareTo(parent.key);

		if (cmpLow < 0) {
			keys(parent.left, list, low, high);
		}

		if (cmpLow <= 0 && cmpHigh >= 0) {
			list.add(parent.key);
		}

		if (cmpHigh > 0) {
			keys(parent.right, list, low, high);
		}
	}

	public String inorder() {
		Map<K, V> map = new LinkedHashMap<>();

		Stack<Node<K, V>> stack = new Stack<>();

		Node<K, V> current = root;
		while (current != null || !stack.isEmpty()) {
			while (current != null) {
				stack.push(current);
				current = current.left;
			}
			if (!stack.isEmpty()) {
				current = stack.pop();
				map.put(current.key, current.value);
				current = current.right;
			}
		}

		return map.toString();
	}

	public int height() {
		System.out.println("root节点：" + root.key);
		System.out.println("左子树的高度：" + height(root.left));
		System.out.println("右子树的高度：" + height(root.right));
		return height(root);
	}

	/**
	 * 传入一个父节点返回该节点的高度
	 * 
	 * @param parent
	 *            父节点
	 * @return 返回以该节点为父节点的子树的高度
	 */
	private int height(Node<K, V> parent) {
		if (parent == null) {
			return 0;
		}

		int left = height(parent.left);
		int right = height(parent.right);
		if (left > right) {
			return left + 1;
		} else {
			return right + 1;
		}
	}
}
