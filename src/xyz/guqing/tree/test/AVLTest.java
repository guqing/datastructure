package xyz.guqing.tree.test;

import org.junit.Before;
import org.junit.Test;

import xyz.guqing.tree.AVLTree;

public class AVLTest {
	private AVLTree<Integer, User> tree = new AVLTree<>();
	@Before
	public void before() {
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
		
//		tree.put(4, user6);
//		tree.put(3, user2);
//		tree.put(6, user1);
//		tree.put(5, user5);
//		tree.put(7, user3);
//		tree.printTree();
//		tree.put(8, user4);
		
//		tree.put(10, user6);
//		tree.put(12, user2);
//		tree.put(8, user1);
//		tree.put(9, user5);
//		tree.put(7, user3);
//		tree.printTree();
//		tree.put(6, user4);
		
		tree.put(10, user6);
		tree.put(7, user2);
		tree.put(11, user1);
		tree.put(6, user5);
		tree.put(8, user3);
		tree.printTree();
		tree.put(9, user4);
	}
	
	@Test
	public void test() {
		System.out.println(tree.height());
		tree.printTree();
		System.out.println(tree.keys());
	}
}
