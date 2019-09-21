package xyz.guqing.tree;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

/**
 * 二叉查找树
 * 
 * @author guqing
 */
public class BinarySearchTree<K extends Comparable<K>, V> {
	private Node<K, V> root;

	private static class Node<K, V> {
		private K key;
		private V value;
		private Node<K, V> left;
		private Node<K, V> right;
		// 以该节点为根的子树中的节点总数
		private int count;

		public Node(K key, V value, int count) {
			this.key = key;
			this.value = value;
			this.count = count;
		}
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

	public void put(K key, V value) {
		// 查找key，如果查找则更新它的值，否则为它创建一个新节点
		root = put(root, key, value);
	}

	/**
	 * 如果key存在与以parent为父节点的子树中则更新它的值
	 * 否则将以key和value为键值创建对应的新节点插入到该子树中
	 * 
	 * @param parent
	 * @param key
	 * @param value
	 * @return
	 */
	private Node<K, V> put(Node<K, V> parent, K key, V value) {
		if (parent == null) {
			return new Node<>(key, value, 1);
		}
		int cmp = key.compareTo(parent.key);
		if (cmp < 0) {
			parent.left = put(parent.left, key, value);
		} else if (cmp > 0) {
			parent.right = put(parent.right, key, value);
		} else {
			parent.value = value;
		}
		// 在一次又一次的递归中更新以该节点为父节点的子树节点总数,因为put一次新创建节点的祖先节点对应的count都需要+1
		parent.count = size(parent.left) + size(parent.right) + 1;
		return parent;
	}

	public K min() {
		return min(root).key;
	}

	/**
	 * 获取最小键,如果根节点的左链接为空，那么一棵二叉查找树
	 * 中最小的键就是根节点，如果左链接非空，那么树中最小键
	 * 就是左子树中的最小键
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

	/**
	 * 向下取整
	 * 
	 * @param key
	 *            向下取整的key
	 * @return 返回向下取整的key,没有找到返回null
	 */
	public K floor(K key) {
		Node<K, V> node = floor(root, key);
		if (node != null) {
			return node.key;
		}
		return null;
	}

	/**
	 * 如果给定的键key小于二叉查找树的根节点的键
	 * 那么小于等于key的最大键floor(key)一定在
	 * 根节点的左子树中;如果给定的键key大于二叉查
	 * 找树的根节点，那么只有当根节点右子树中存在
	 * 小于等于key的节点时,小于等于key的最大键才
	 * 会出现在右子树中,否则根节点就是小于等于key
	 * 的最大键
	 * 
	 * @return
	 */
	private Node<K, V> floor(Node<K, V> parent, K key) {
		if (parent == null) {
			return null;
		}

		int cmp = key.compareTo(parent.key);

		if (cmp == 0) {
			// 相等
			return parent;
		}
		// 在左子树寻找
		if (cmp < 0) {
			return floor(parent.left, key);
		}

		Node<K, V> temp = floor(parent.right, key);
		if (temp != null) {
			return temp;
		}

		return parent;
	}

	/**
	 * 向上取整
	 * 
	 * @param key
	 *            向上取整的key
	 * @return 返回向上取整得到的key,没有找到返回null
	 */
	public K ceiling(K key) {
		Node<K, V> node = ceiling(root, key);
		if (node != null) {
			return node.key;
		}
		return null;
	}

	/**
	 * 在floor()的代码上将>和<互换以及left和right互换实现
	 * 
	 * @param parent
	 *            父节点
	 * @param key
	 *            查找的key
	 * @return 返回向上取整的节点
	 */
	public Node<K, V> ceiling(Node<K, V> parent, K key) {
		if (parent == null) {
			return null;
		}

		int cmp = key.compareTo(parent.key);

		if (cmp == 0) {
			// 相等
			return parent;
		}
		// 在右子树寻找
		if (cmp > 0) {
			return floor(parent.right, key);
		}

		Node<K, V> temp = floor(parent.left, key);
		if (temp != null) {
			return temp;
		}

		return parent;
	}

	public K select(int k) {
		Node<K, V> node = select(root, k);
		if (node != null) {
			return node.key;
		}
		return null;
	}

	/**
	 * 返回排名为k的节点
	 * 
	 * @param parent
	 *            父节点
	 * @param k
	 *            排名
	 * @return 返回排名为k的节点,未找到返回null
	 */
	private Node<K, V> select(Node<K, V> parent, int k) {
		if (parent == null) {
			return null;
		}
		int count = size(parent.left);
		if (count > k) {
			return select(parent.left, k);
		} else if (count < k) {
			return select(parent.right, k - count - 1);
		} else {
			return parent;
		}
	}

	/**
	 * @param key
	 * @return 返回以root为节点的子树中小于root.key的数量
	 */
	public int rank(K key) {
		return rank(root, key);
	}

	/**
	 * @param parent
	 * @param key
	 * @return 返回以parent为节点的子树中小于parent.key的数量
	 */
	private int rank(Node<K, V> parent, K key) {
		if (parent == null) {
			return 0;
		}
		int cmp = key.compareTo(parent.key);
		if (cmp < 0) {
			return rank(parent.left, key);
		} else if (cmp > 0) {
			return 1 + size(parent.left) + rank(parent.right, key);
		} else {
			return size(parent.left);
		}
	}

	public void delete(K key) {
		root = delete(root, key);
	}

	private Node<K, V> delete(Node<K, V> parent, K key) {
		if (parent == null) {
			return null;
		}
		int cmp = key.compareTo(parent.key);
		if (cmp < 0) {
			parent.left = delete(parent.left, key);
		} else if (cmp > 0) {
			parent.right = delete(parent.right, key);
		} else {
			// 这里就是删除对应的三种情况
			if (parent.right == null) {
				return parent.left;
			}

			if (parent.left == null) {
				return parent.right;
			}

			Node<K, V> temp = parent;
			// 找到最小节点并删除最小节点
			parent = min(temp.right);
			parent.right = deleteMin(temp.right);

			parent.left = temp.left;
		}
		parent.count = size(parent.left) + size(parent.right) + 1;
		return parent;
	}

	/**
	 * 删除最小键
	 */
	public void deleteMin() {
		root = deleteMin(root);
	}

	private Node<K, V> deleteMin(Node<K, V> parent) {
		if (parent.left == null) {
			return parent.right;
		}
		parent.left = deleteMin(parent.left);
		// 同样的删除也需要逐一更新节点的count
		parent.count = size(parent.left) + size(parent.right) + 1;
		return parent;
	}

	/**
	 * 删除最大键
	 */
	public void deleteMax() {
		root = deleteMax(root);
	}

	/**
	 * 与deleteMin类似只需要将left和right互换即可
	 * 
	 * @param parent
	 * @return
	 */
	private Node<K, V> deleteMax(Node<K, V> parent) {
		if (parent.right == null) {
			return parent.left;
		}
		parent.right = deleteMax(parent.right);
		// 同样的删除也需要逐一更新节点的count
		parent.count = size(parent.left) + size(parent.right) + 1;
		return parent;
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
}
