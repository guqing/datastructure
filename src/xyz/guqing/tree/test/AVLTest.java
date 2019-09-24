package xyz.guqing.tree.test;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import xyz.guqing.tree.AVLTree;

public class AVLTest {
	private AVLTree<Integer, User> tree = new AVLTree<>();
	private Map<Integer, User> map = new HashMap<>();
	
	@Before
	public void before() {
		User user1 = new User();
		user1.setId(1);
		user1.setUsername("王五");
		map.put(1, user1);
		
		User user2 = new User();
		user2.setId(2);
		user2.setUsername("zhangsan");
		map.put(2, user2);
		
		User user3 = new User();
		user3.setId(3);
		user3.setUsername("李四");
		map.put(3, user3);
		
		User user4 = new User();
		user4.setId(4);
		user4.setUsername("王二狗");
		map.put(4, user4);
		
		User user5 = new User();
		user5.setId(5);
		user5.setUsername("赵六");
		map.put(5, user5);
		
		User user6 = new User();
		user6.setId(6);
		user6.setUsername("唐三藏");
		map.put(6, user6);
		
		User user7 = new User();
		user7.setId(7);
		user7.setUsername("孙悟空");
		map.put(7, user7);
		
		User user8 = new User();
		user8.setId(8);
		user8.setUsername("猪八戒");
		map.put(8, user8);
	}
	
	@Test
	public void testPut1() {
		// 使用第1组测试数据
		putUserGroup1();
		
		System.out.println(tree.height());
		tree.printTree();
		System.out.println(tree.keys());
	}
	
	@Test
	public void testPut2() {
		// 使用第2组测试数据
		putUserGroup2();
		
		System.out.println(tree.height());
		tree.printTree();
		System.out.println(tree.keys());
	}
	
	@Test
	public void testPut3() {
		// 使用第3组测试数据
		putUserGroup3();
		
		System.out.println(tree.height());
		tree.printTree();
		System.out.println(tree.keys());
	}
	
	@Test
	public void testDelete1() {
		// 使用第1组测试数据
		putUserGroup1();
		
		System.out.println("平衡的二叉查找树：");
		System.out.println(tree.height());
		tree.printTree();
		
		System.out.println("删除4节点后：");
		tree.delete(4);
		tree.printTree();
		
		System.out.println("删除7节点后：");
		tree.delete(7);
		tree.printTree();
		System.out.println(tree.height());
	}
	
	@Test
	public void testDelete2() {
		// 使用第2组测试数据
		putUserGroup2();
		
		System.out.println("平衡的二叉查找树：");
		System.out.println(tree.height());
		tree.printTree();
		
		System.out.println("删除6节点后：");
		tree.delete(6);
		tree.printTree();
		
		System.out.println("删除7节点后：");
		tree.delete(7);
		tree.printTree();
		System.out.println(tree.height());
	}
	
	@Test
	public void testDelete3() {
		// 使用第3组测试数据
		putUserGroup3();
		
		System.out.println("平衡的二叉查找树：");
		System.out.println(tree.height());
		tree.printTree();
		
		System.out.println("删除9节点后：");
		tree.delete(9);
		tree.printTree();
		
		System.out.println("删除11节点后：");
		tree.delete(11);
		tree.printTree();
		System.out.println(tree.height());
		
		System.out.println("删除10节点后：");
		tree.delete(10);
		tree.printTree();
		System.out.println(tree.height());
	}
	
	@Test
	public void testBigTree() {
		// 为了测试平衡性，构建一棵大树
		createBigTree();
		
		System.out.println("随便删除几个上层节点：" );
		tree.delete(41);
		tree.delete(15);
		tree.delete(72);
		tree.printTree();
		// 如果出现不平衡的情况height执行后会报错,内有Assert
		System.out.println(tree.height());
	}
	
	private void putUserGroup1() {
		tree.put(4, map.get(4));
		tree.put(3, map.get(3));
		tree.put(6, map.get(6));
		tree.put(5, map.get(5));
		tree.put(7, map.get(7));
		tree.put(8, map.get(8));
	}
	
	private void putUserGroup2() {
		tree.put(10, map.get(10));
		tree.put(12, map.get(12));
		tree.put(8, map.get(8));
		tree.put(9, map.get(9));
		tree.put(7, map.get(7));
		tree.put(6, map.get(6));
	}
	
	private void putUserGroup3() {
		tree.put(10, map.get(10));
		tree.put(7, map.get(7));
		tree.put(11, map.get(11));
		tree.put(6, map.get(6));
		tree.put(8, map.get(8));
		tree.put(9, map.get(9));
	}
	
	private void createBigTree() {
		tree.put(41, map.get(10));
		tree.put(20, map.get(12));
		tree.put(11, map.get(8));
		tree.put(29, map.get(9));
		tree.put(5, map.get(7));
		tree.put(15, map.get(6));
		tree.put(7, map.get(6));
		tree.put(12, map.get(6));
		tree.put(29, map.get(6));
		tree.put(27, map.get(6));
		tree.put(32, map.get(6));
		tree.put(34, map.get(6));
		tree.put(65, map.get(6));
		tree.put(50, map.get(6));
		tree.put(91, map.get(6));
		tree.put(72, map.get(6));
		tree.put(99, map.get(6));
		tree.put(67, map.get(6));
		tree.put(76, map.get(6));
	}
}
