package xyz.guqing.tree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 哈夫曼树
 * @author guqing
 */
public class HuffmanTree {
	private Node root;
	
	private class Node implements Comparable<Node>{
		private int value;
		private Node left;
		private Node right;
		
		public Node(int value) {
			this.value = value;
		}
        
		@Override
		public int compareTo(Node that) {
			 return this.value - that.value;
		}
	}
	
	
	public HuffmanTree(int[] array) {
		root = buildHuffman(array);
	}
	
	private Node buildHuffman(int[] array) {
		List<Node> nodes = new ArrayList<>();
		// 遍历array
		for(int i=0; i< array.length; i++) {
			// 将array的每个元素构建成一个Node并放入到ArrayList中
			nodes.add(new Node(array[i]));
		}
		
		while(nodes.size() > 1) {
			// 对集合进行排序
			Collections.sort(nodes);
			
			// 取出根节点权值最小的两棵二叉树(单个节点也可以看成最简单的二叉树)
			Node leftNode = nodes.get(0);
			Node rightNode = nodes.get(1);
			
			// 构建一棵新的二叉树
			Node parent = new Node(leftNode.value + rightNode.value);
			parent.left = leftNode;
			parent.right = rightNode;
			
			// 从nodes集合中删除leftNode和rightNode
			nodes.remove(leftNode);
			nodes.remove(rightNode);
			
			// 将parent加入到nodes中
			nodes.add(parent);
		}
		
		// 返回哈夫曼树的root节点
		return nodes.get(0);
	}
	
	public String preorder() {
		List<Integer> list = new ArrayList<>();
		preorder(root, list);
		return list.toString();
	}
	
	private void preorder(Node parent, List<Integer> list) {
		list.add(parent.value);
		if(hasLeftChild(parent)) {
			preorder(parent.left, list);
		}
		
		if(hasRightChild(parent)) {
			preorder(parent.right, list);
		}
	}
	private boolean hasLeftChild(Node parent) {
		if(parent.left != null) {
			return true;
		}
		return false;
	}
	
	private boolean hasRightChild(Node parent) {
		if(parent.right != null) {
			return true;
		}
		return false;
	}
	
	public static void main(String[] args) {
		int[] array = {13, 7, 8, 3, 29, 6, 1};
		HuffmanTree huffmanTree = new HuffmanTree(array);
		System.out.println(huffmanTree.preorder());
	}
}
