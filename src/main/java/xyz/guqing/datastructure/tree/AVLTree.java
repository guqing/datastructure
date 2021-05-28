package xyz.guqing.datastructure.tree;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import org.junit.Assert;


/**
 * 平衡查找树
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
	    if (key == null) {
	        throw new NullPointerException();
	    }
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
	private Node<K,V> put(Node<K,V> parent, K key, V value) {
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

	    // 高度大于1,需要旋转以达平衡
	    if (Math.abs(height(parent.left) - height(parent.right)) > 1) {
	    	parent = putBalance(parent);
	    }
	    
	    // 在一次又一次的递归中更新以该节点为父节点的子树节点总数,因为put一次新创建节点的祖先节点对应的count都需要+1
	    parent.count = size(parent.left) + size(parent.right) + 1;
	    return parent;
	}


	/**
	 * 维护平衡查找树的平衡:
	 * 任意一次插入所能造成平衡查找树的不平衡因素
	 * 都可以简化为下述四种范型之一:
	 * <li>LL型</li>
	 * <li>LR型</li>
	 * <li>RR型</li>
	 * <li>RL型</li>
	 */
	private Node<K,V> putBalance(Node<K,V> parent) {
		// 这里可以将LL和LR合并为在一个if里面，独立写方便看
	    // LL
	    if (height(parent.left) > height(parent.right) &&
	            height(parent.left.left) > height(parent.left.right)) {
	    	// 右旋
	        parent = rightRotate(parent);
	        return parent;
	    }
	    
	    // LR
	    if (height(parent.left) > height(parent.right) &&
	            height(parent.left.right) > height(parent.left.left)) {
	    	parent = leftRightRotate(parent);
	    	return parent;
	    }
	    
	    // RR,RR和RL也可以合并在一个一个if里面独立写方便看
	    if (height(parent.right) > height(parent.left) &&
	            height(parent.right.right) > height(parent.right.left)) {
	    	// 左旋
	    	parent = leftRotate(parent);
	    	return parent;
	    }
	    
	    // RL
	    if (height(parent.right) > height(parent.left) &&
	            height(parent.right.left) > height(parent.right.right)) {
	    	// 右左旋
	    	parent = rightLeftRotate(parent);
	    	return parent;
	    }
	    return parent;
	}
	
	private Node<K,V> deleteBalance(Node<K,V> node) {
	    // LL & L
	    if (height(node.left) > height(node.right) &&
	            height(node.left.left) >= height(node.left.right)) {
	    	// 右旋
	    	node = rightRotate(node);
	    	return node;
	    }
	    // LR
	    if (height(node.left) > height(node.right) &&
	            height(node.left.right) > height(node.left.left)) {
	    	// 左右旋
	    	node = leftRightRotate(node);
	    	return node;
	    }
	    // RR & R
	    if (height(node.right) > height(node.left) &&
	            height(node.right.right) >= height(node.right.left)) {
	    	// 左旋
	    	node = leftRotate(node);
	    	return node;
	    }
	    // RL
	    if (height(node.right) > height(node.left) &&
	            height(node.right.left) > height(node.right.right)) {
	    	// 右左旋
	    	node = rightLeftRotate(node);
	    	return node;
	    }
	    return node;
	}
	
	/**
	 * 左旋转
	 * @param parent 父节点
	 * @return 返回修改后的父节点
	 */
	private Node<K,V> leftRotate(Node<K,V> parent) {
		Node<K,V> temp = parent.right;
		parent.right = temp.left;
		temp.left = parent;
		temp.count = parent.count;
		parent.count = 1 + size(parent.left) + size(parent.right);
		return temp;
	}
	
	/**
	 * 右旋转
	 * @param parent 传入一个父节点
	 * @return 返回传入的参数节点
	 */
	private Node<K,V> rightRotate(Node<K,V> parent) {
		Node<K,V> temp = parent.left;
		
		parent.left = temp.right;
		temp.right = parent;
		
		temp.count = parent.count;
		
		parent.count = 1 + size(parent.left) + size(parent.right);
		return temp;
	}
	
	
	/**
	 * 左右旋
	 * @param parent
	 * @return
	 */
	private Node<K,V> leftRightRotate(Node<K,V> parent) {
		Node<K,V> nodeH = null;
	    Node<K,V> nodeX = null;
		nodeH = parent.left;
    	nodeX = parent.left.right;
        parent.left = nodeX.right;
        nodeH.right = nodeX.left;
        nodeX.left = nodeH;
        nodeX.right = parent;

        parent.count = size(parent.left) + size(parent.right) + 1;
        nodeH.count = size(nodeH.left) + size(nodeH.right) + 1;
        return nodeX;
	}
	
	/**
	 * 右左旋
	 * @param parent
	 * @return
	 */
	private Node<K,V> rightLeftRotate(Node<K,V> parent) {
		Node<K,V> nodeH = null;
	    Node<K,V> nodeX = null;
		nodeH = parent.right;
    	nodeX = parent.right.left;
        parent.right = nodeX.left;
        nodeH.left = nodeX.right;
        nodeX.left = parent;
        nodeX.right = nodeH;

        parent.count = size(parent.left) + size(parent.right) + 1;
        nodeH.count = size(nodeH.left) + size(nodeH.right) + 1;
        return nodeX;
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
		Assert.assertTrue(Math.abs(height(root.left) - height(root.right)) < 2);
		
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
	
	/**
	 * 中序遍历
	 * @return 返回中序遍历的字符串结果
	 */
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
	
	/**
	 * 根据key删除
	 * @param key
	 */
	public void delete(K key) {
	    if (key == null) {
	        throw new NullPointerException();
	    }
	    root = delete(root, key);
	}

	private Node<K,V> delete(Node<K,V> node, K key) {
	    if (node == null) {
	        return null;
	    }

	    int cmp = key.compareTo(node.key);
	    if (cmp < 0) {
	    	node.left = delete(node.left, key);
		} else if (cmp > 0) {
			node.right = delete(node.right, key);
		} else {
			// 这里就是删除对应的三种情况
			if (node.right == null) {
				return node.left;
			}

			if (node.left == null) {
				return node.right;
			}

			Node<K, V> temp = node;
			// 找到最小节点并删除最小节点
			node = min(temp.right);
			node.right = deleteMin(temp.right);

			node.left = temp.left;
	    }

	    if (Math.abs(height(node.left) - height(node.right)) > 1) {
	        node = deleteBalance(node);
	    }
	    node.count = 1 + size(node.left) + size(node.right);
	    return node;
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
	 * 打印树的形状
	 */
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
