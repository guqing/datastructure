package xyz.guqing.tree;

import org.junit.Before;
import org.junit.Test;

public class BSTTest {
	private BinarySearchTree<String, User> tree = new BinarySearchTree<>();
	@Before
	public void prepareData() {
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
		
		//A1 B2 C3 D4 E5 F6 G7 H8
		//I9 J10 K11 L12 M13 N14 O15 P16 Q17 R18 S19 T20 U21 V22 W23 X24
		//A1 D4 E5 G7 H8 R18 M13 S19 X24
		tree.put("F", user6);
		tree.put("B", user2);
		tree.put("A", user1);
		tree.put("E", user5);
		tree.put("C", user3);
		tree.put("D", user4);
		tree.put("G", user7);
		tree.put("H", user8);
		
		// tree.put("S", user6);
		// tree.put("E", user2);
		// tree.put("A", user1);
		// tree.put("C", user5);
		// tree.put("R", user3);
		// tree.put("H", user4);
		// tree.put("M", user7);
		// tree.put("X", user8);
		
		/**
		 * 树结构：
		 *      F
		 * 	 B     G
		 * A    E    H
		 *     C
		 *      D
		 */
	}
	
	@Test
	public void testInorder() {
		// 中序遍历是按正常排序，因为打印是A B C D E F G H
		System.out.println(tree.inorder());
	}
	
	@Test
	public void testGet() {
		// id=1
		System.out.println(tree.get("A"));
		// id=2
		System.out.println(tree.get("B"));
		// id=3
		System.out.println(tree.get("C"));
		// id=4
		System.out.println(tree.get("D"));
	}
	
	@Test
	public void testSize() {
		System.out.println("树节点个数：" + tree.size());
	}
	
	@Test
	public void testKeys() {
		// keys:[A, B, C, D, E, F, G, H]
		System.out.println("keys:" + tree.keys());
		// keyByRang:[A, B, C, D]
		System.out.println("keyByRang:" + tree.keys("A", "D"));
	}
	
	@Test
	public void testMinAndMax() {
		System.out.println("min:" + tree.min());
		System.out.println("max:" + tree.max());
	}
	
	@Test
	public void testDelete() {
		// 中序遍历[A, B, C, D, E, F, G, H]
		System.out.println(tree.keys());
		// 删除最小key
		tree.deleteMin();
		// 删除最大key
		tree.deleteMax();
		
		// 执行key删除,删除父节点B
		tree.delete("B");
		
		// [C, D, E, F, G]
		System.out.println(tree.keys());
	}
	
	@Test
	public void testRankAndSelect() {
		// 返回指定键的排名:0,1,2,3
		System.out.println(tree.rank("A"));
		System.out.println(tree.rank("B"));
		System.out.println(tree.rank("C"));
		System.out.println(tree.rank("D"));
		
		// 返回排名为k的键,和rank()功能正好相反
		System.out.println(tree.select(0));
		System.out.println(tree.select(1));
		System.out.println(tree.select(2));
		System.out.println(tree.select(3));
	}
	
	@Test
	public void testFloorAndCeiling() {
		/**
		 * 为了测试处向上取整和向下取整需要存储的数据不连续才能看到效果
		 * 因此重新构建一棵树
		 * 树结构：S E A C R H M X
		 *      S
		 * 	 E     X
		 * A     R
		 *  C  H
		 *       M
		 */
		// 结果E
		System.out.println(tree.floor("G"));
		// 结果：M
		System.out.println(tree.ceiling("P"));
	}
}
