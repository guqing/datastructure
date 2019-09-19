package xyz.guqing.tree;

/**
 * 二叉树
 * @author guqing
 */
public class BinaryTree<T extends Comparable<T>> {
	// 树的根节点
	private Node<T> root;
	private int count = 0;
	
	public Node<T> getRoot() {
		return root;
	}
	
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
	
	/**
	 * 先序遍历
	 */
	public void preorder(Node<T> parent) {
		visit(parent);
		if(hasLeftChild(parent)) {
			preorder(parent.left);
		}
		
		if(hasRightChild(parent)) {
			preorder(parent.right);
		}
	}
	
	public void inorder(Node<T> parent) {
		if(hasLeftChild(parent)) {
			inorder(parent.left);
		}
		visit(parent);
		
		if(hasRightChild(parent)) {
			inorder(parent.right);
		}
	}
	
	public void subsequent(Node<T> parent) {
		if(hasLeftChild(parent)) {
			subsequent(parent.left);
		}
		
		if(hasRightChild(parent)) {
			subsequent(parent.right);
		}
		
		visit(parent);
	}
	
	private void visit(Node<T> parent) {
		System.out.println(parent.data + " ");
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
		User user3 = new User();
		user3.setId(3);
		user3.setUsername("王五");
		
		User user1 = new User();
		user1.setId(1);
		user1.setUsername("zhangsan");
		
		User user2 = new User();
		user2.setId(2);
		user2.setUsername("李四");
		
		User user4 = new User();
		user4.setId(4);
		user4.setUsername("王二狗");
		
		BinaryTree<User> tree = new BinaryTree<>();
		tree.add(user3);
		tree.add(user1);
		tree.add(user2);
		tree.add(user4);
		
		System.out.println("size:" + tree.size());
		System.out.println("先序遍历：");
		tree.preorder(tree.getRoot());
		System.out.println("中序遍历：");
		tree.inorder(tree.getRoot());
		System.out.println("后序遍历：");
		tree.subsequent(tree.getRoot());
	}
}
