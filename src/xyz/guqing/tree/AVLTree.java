package xyz.guqing.tree;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

/**
 * 平衡查找树
 * 
 * @author guqing
 */
public class AVLTree<K extends Comparable<K>, V> {
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
		
		if(height(parent.right) - height(parent.left) > 1) {
			// 如果它的右子树的左子树的高度大于它的右子树的高度
			if(height(parent.right.left) > height(parent.right)) {
				// 先对当前节点的右子节点进行右旋转
				parent = leftRotate(parent.right);
				// 在对当前节点进行左旋转
				parent = leftRotate(parent);
			} else {
				// 左旋
				parent = leftRotate(parent);
			}
		}
		
		if(height(parent.left) - height(parent.right) > 1) {
			// 如果它的左子树的右子树的高度大于它的左子树的高度
			if(height(parent.left.right) > height(parent.left)) {
				// 先对当前节点的左节点进行左旋转
				parent = leftRotate(parent.left);
				// 在对当前节点进行右旋转
				parent = rightRotate(parent);
			} else {
				parent = rightRotate(parent);
			}
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

	public int height() {
		System.out.println("root节点：" + root.key);
		System.out.println("左子树的高度：" + height(root.left));
		System.out.println("右子树的高度：" + height(root.right));
		return height(root);
	}
	/**
	 * 传入一个父节点返回该节点的高度
	 * @param parent 父节点
	 * @return 返回以该节点为父节点的子树的高度
	 */
	private int height(Node<K,V> parent) {
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
	
	public void leftRotate() {
		root = leftRotate(root);
	}
	
	public void rightRotate() {
		root = rightRotate(root);
	}
	
	private Node<K,V> leftRotate1(Node<K,V> parent) {
		// 创建新的节点,以当前根节点的值创建
		Node<K,V> temp = new Node<K,V>(parent.key,parent.value,1);
		// 把新节点的左子树设置为当前节点的左子树
		temp.left = parent.left;
		// 把新的节点的右子树设置为当前节点的右子树的左子树
		temp.right = parent.right.left;
		// 把当前节点的值替换为右子树的值
		parent.key = parent.right.key;
		parent.value = parent.right.value;
		parent.count = parent.right.count;
		// 把当前节点的右子树设置成当前节点的右子树的右子树
		parent.right = parent.right.right;
		// 把当前节点的左子节点设置为新的节点
		parent.left = temp;
		parent.count = 1 + size(parent.left) + size(parent.right);
		return temp;
	}
	
	private Node<K,V> leftRotate(Node<K,V> parent) {
		Node<K,V> temp = parent.right;
		parent.right = temp.left;
		temp.left = parent;
		temp.count = parent.count;
		parent.count = 1 + size(parent.left) + size(parent.right);
		return temp;
	}
	
	private Node<K,V> rightRotate1(Node<K,V> parent) {
		Node<K,V> temp = new Node<K,V>(parent.key,parent.value,1);
		temp.right = parent.right;
		temp.left = parent.left.right;
		 
		parent.key = parent.left.key;
		parent.value = parent.left.value;
		parent.count = parent.left.count;
		
		parent.left = parent.left.left;
		
		parent.right = temp;
		parent.count = 1 + size(parent.left) + size(parent.right);
		
		return temp;
	}
	
	private Node<K,V> rightRotate(Node<K,V> parent) {
		Node<K,V> temp = parent.left;
		
		parent.left = temp.right;
		temp.right = parent;
		
		temp.count = parent.count;
		
		parent.count = 1 + size(parent.left) + size(parent.right);
		return temp;
	}
	
	public void printTree() {
        //获得二叉树高度
        int height = height(root);
        //申请数组
        Object[] arr = new Object[2 << height - 1];
        //链表转二叉树数组
        linkedTransferArray(arr, root, 1);

        //长度
        int maxSpaceCount = 2 << height;

        //遍历数组
        for (int i = 0; i < height; i++) {
            StringBuffer sb = new StringBuffer();
            int nodes = (((2 << (i - 1)) + 1) == 1 ? 2 : ((2 << (i - 1)) + 1));
            if (i == height - 1) nodes -= 1;
            int spaceCount = (maxSpaceCount - nodes) / nodes;

            //打印前面空格
            for (int i1 = 0; i1 < spaceCount; i1++) {
                sb.append(" ");
            }
            //打印数组内容
            int indexLimit = (2 << i) - 1;
            int i3 = ((2 << (i - 1)) - 1) == -1 ? 0 : (2 << (i - 1)) - 1;
            for (int i1 = i3; i1 < indexLimit; i1++) {
                if (arr[i1] != null) sb.append(arr[i1]);

                for (int i2 = 0; i2 < spaceCount; i2++) sb.append(" ");
            }

            System.out.println(sb);

            sb = new StringBuffer();

            //打印前面空格
            nodes = (((2 << i) + 1) == 1 ? 2 : ((2 << i) + 1));
            if (i == height - 2) nodes -= 1;
            spaceCount = (maxSpaceCount - nodes) / nodes;
            for (int i1 = 0; i1 < spaceCount; i1++) {
                sb.append(" ");
            }

            //打印树枝
            for (int i1 = i3; i1 < indexLimit; i1++) {
                if (arr.length > i1 * 2 + 1 && arr[i1 * 2 + 1] != null) sb.append("/");
                //树枝前面间隔
                for (int i2 = 0; i2 < spaceCount; i2++) sb.append(" ");

                if (arr.length > (i1 * 2 + 2) && arr[i1 * 2 + 2] != null) sb.append("\\");
                //树枝后面间隔
                for (int i2 = 0; i2 < spaceCount; i2++) sb.append(" ");
            }

            System.out.println(sb);
        }
    }
	 
	//将树结构转换成数组结构，方便打印（非正常排序）
    private void linkedTransferArray(Object[] arr, Node<K, V> node, int index) {
        if (node == null) return;

        arr[index - 1] = node.key;

        if (node.left != null)
            linkedTransferArray(arr, node.left, index * 2);
        if (node.right != null)
            linkedTransferArray(arr, node.right, index * 2 + 1);
    }
}
