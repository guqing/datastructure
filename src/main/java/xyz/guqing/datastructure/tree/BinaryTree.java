package xyz.guqing.datastructure.tree;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import xyz.guqing.datastructure.tree.model.User;

/**
 * 二叉树
 * @author guqing
 */
public class BinaryTree<T extends Comparable<T>> {
	// 树的根节点
	private Node<T> root;
	private int count = 0;
	
	/**
	 * 添加数据方法
	 * @param t 数据对象
	 */
	public void add(T t) {
		if(t == null) {
			throw new NullPointerException();
		}
		
		Node<T> node = new Node<>(t);
		if(root == null) {
			// 当前树为空
			root = node;
			count++;
			return;
		}
		// 树不为空，遍历树
		Node<T> current = root;
		// 指向父节点的指针
		Node<T> parent = null;
		while(current != null) {
			parent = current;
			if(t.compareTo(parent.data) < 0) {
				//t小于current向左遍历
				current = current.left;
				if(current == null) {
					// 找到插入位置
					parent.left = node;
					count++;
				}
			}
			
			if(t.compareTo(parent.data) > 0) {
				//t小于current向右遍历
				current = current.right;
				if(current == null) {
					// 找到插入位置
					parent.right = node;
					count++;
				}
			}
		}
	}
	
	private boolean hasLeftChild(Node<T> parent) {
		if(parent.left != null) {
			return true;
		}
		return false;
	}
	
	private boolean hasRightChild(Node<T> parent) {
		if(parent.right != null) {
			return true;
		}
		return false;
	}
	
	public int size() {
		return count;
	}
	
	public boolean isEmpty() {
		return count == 0;
	}
	
	public int depth() {
		return depth(root);
	}
	
	/**
     * 递归方式返回树的深度
     * 首先要一种获取以某个节点为子树的高度的方法，使用递归调用。
     * 如果一个节点为空，那么这个节点肯定是一颗空树，高度为0；
     * 如果不为空，那么我们要遍历地比较它的左子树高度和右子树高度，
     * 高的一个为这个子树的最大高度，然后加上自己本身的高度就是了
     * 获取二叉树的高度，只需要调用第一种方法，即传入根节点
     * @param parent 父节点
     * @return 返回树的深度
     */
	private int depth(Node<T> parent) {
        if (parent == null) {
            return 0;
        }
 
        int left = depth(parent.left);
        int right = depth(parent.right);
        if (left > right) {
            return left + 1;
        } else {
            return right + 1;
        }
    }
	
	/**
	 * 先序遍历包装方法,包装的目的在于省去调用时传入根节点
	 * @return 返回遍历字符串
	 */
	public String preorderByRecursion() {
		List<T> list = new ArrayList<>();
		preorder(root, list);
		return list.toString();
	}
	
	/**
	 * 先序遍历递归实现
	 */
	private void preorder(Node<T> parent, List<T> list) {
		visit(parent, list);
		if(hasLeftChild(parent)) {
			preorder(parent.left, list);
		}
		
		if(hasRightChild(parent)) {
			preorder(parent.right, list);
		}
	}
	
	/**
	 * 先序遍历非递归方式实现
	 */
	public String preorder() {
		List<T> list = new ArrayList<>();
		
		Stack<Node<T>> stack = new Stack<Node<T>>();
		Node<T> current = root;
        while(current != null || !stack.isEmpty()) {
        	while (current != null) {
        		list.add(current.data);
                stack.push(current);
                current = current.left;
            }
        	
            if(!stack.isEmpty()) {
                Node<T> node = stack.pop();
                current = node.right;
            }
        }
		return list.toString();
	}
	
	/**
	 * 中序遍历的包装方法
	 * @return 返回结果字符串
	 */
	public String inorderByRecursion() {
		List<T> list = new ArrayList<>();
		inorder(root, list);
		return list.toString();
	}
	
	/**
	 * 中序遍历递归方式实现
	 * @param parent 父节点
	 */
	private void inorder(Node<T> parent, List<T> list) {
		if(hasLeftChild(parent)) {
			inorder(parent.left, list);
		}
		visit(parent, list);
		
		if(hasRightChild(parent)) {
			inorder(parent.right, list);
		}
	}
	
	/**
	 * 非递归方式中序遍历
	 * @return 返回遍历结果的字符串
	 */
	public String inorder() {
		List<T> list = new ArrayList<>();
		
        Stack<Node<T>> stack = new Stack<>();
        
        Node<T> current = root;
        while (current != null || !stack.isEmpty()) {
            while (current != null) {
                stack.push(current);
                current = current.left;
            }
            if (!stack.isEmpty()) {
            	current = stack.pop();
            	list.add(current.data);
            	current = current.right;
            }
        }
        
        return list.toString();
    }
	
	/**
	 * 后续遍历递归方式的包装方法
	 * @return 返回遍历结果的字符串
	 */
	public String subsequentByRecursion() {
		List<T> list = new ArrayList<>();
		subsequent(root, list);
		return list.toString();
	}
	
	/**
	 * 后续遍历递归方式实现
	 * @param parent 父节点
	 */
	private void subsequent(Node<T> parent, List<T> list) {
		if(hasLeftChild(parent)) {
			subsequent(parent.left, list);
		}
		
		if(hasRightChild(parent)) {
			subsequent(parent.right, list);
		}
		
		visit(parent, list);
	}
	
	/**
	 * 非递归方式后续遍历
	 * @return 返回遍历结果字符串
	 */
	public String subsequent() {
		List<T> list = new ArrayList<>();
		Stack<Node<T>> stack = new Stack<>();
		
		Node<T> current = root;
		Node<T> prev = current;
        while(current != null || !stack.isEmpty()) {
            while(current != null) {
                stack.push(current);
                current = current.left;
            }

            if(!stack.isEmpty()) {
            	Node<T> temp = stack.peek().right;
                if(temp == null || temp == prev) {
                	current = stack.pop();
                    list.add(current.data);
                    prev = current;
                    current = null;
                } else {
                	current = temp;
                }
            }
        }
        return list.toString();
    }
	
	/**
	 * 将遍历到的节点数据添加到集合中
	 * @param parent 父节点
	 */
	private void visit(Node<T> parent, List<T> list) {
		list.add(parent.data);
	}
	
	private static class Node<T> {
		// 左子树
		Node<T> left;
		// 右子树
		Node<T> right;
		// 数据
		T data;
		public Node(T data) {
			this.data = data;
		}
	}
	
	public static void main(String[] args) {
		User user6 = new User();
		user6.setId(6);
		user6.setUsername("王五");
		
		User user2 = new User();
		user2.setId(2);
		user2.setUsername("zhangsan");
		
		User user1 = new User();
		user1.setId(1);
		user1.setUsername("李四");
		
		User user5 = new User();
		user5.setId(5);
		user5.setUsername("王二狗");
		
		User user3 = new User();
		user3.setId(3);
		user3.setUsername("赵六");
		
		User user4 = new User();
		user4.setId(4);
		user4.setUsername("唐三藏");
		
		User user7 = new User();
		user7.setId(7);
		user7.setUsername("孙悟空");
		
		User user8 = new User();
		user8.setId(8);
		user8.setUsername("猪八戒");
		
		BinaryTree<User> tree = new BinaryTree<>();
		tree.add(user6);
		tree.add(user2);
		tree.add(user1);
		tree.add(user5);
		tree.add(user3);
		tree.add(user4);
		tree.add(user7);
		tree.add(user8);
		
		/**
		 * 树结构：
		 *      6
		 * 	 2     7
		 * 1    5    8
		 *     3
		 *      4
		 */
		
		
		System.out.println("size:" + tree.size());
		// 结果： 8
		
		System.out.println("非递归方式先序遍历:" + tree.preorder());
		System.out.println("递归方式先序遍历：" + tree.preorderByRecursion());
		// 结果: 6 2 1 5 3 4 7 8
		
		System.out.println("非递归方式中序遍历:" + tree.inorder());
		System.out.println("递归中序遍历：" + tree.inorderByRecursion());
		// 结果: 1 2 3 4 5 6 7 8
		
		System.out.println("非递归方式后序遍历" + tree.subsequent());
		System.out.println("递归后序遍历：" + tree.subsequentByRecursion());
		// 结果: 1 4 3 5 2 8 7 6
		
		System.out.println("树的深度:" + tree.depth());
		// 结果：5
	}
}
