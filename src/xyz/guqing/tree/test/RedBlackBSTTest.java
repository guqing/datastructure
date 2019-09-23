package xyz.guqing.tree.test;

import org.junit.Before;
import org.junit.Test;

import xyz.guqing.tree.RedBlackBST;

/**
 * 红黑树测试
 * @author guqing
 */
public class RedBlackBSTTest {
	private RedBlackBST<String, User> tree = new RedBlackBST<>();
	
	@Before
	public void before() {
		User user1 = new User();
		user1.setId(1);
		user1.setUsername("王五");
		
		User user2 = new User();
		user2.setId(2);
		user2.setUsername("zhangsan");
		
		User user3 = new User();
		user3.setId(3);
		user3.setUsername("李四");
		
		User user4 = new User();
		user4.setId(4);
		user4.setUsername("王二狗");
		
		User user5 = new User();
		user5.setId(5);
		user5.setUsername("沙和尚");
		
		User user6 = new User();
		user6.setId(6);
		user6.setUsername("赵六");
		
		User user7 = new User();
		user7.setId(7);
		user7.setUsername("唐三藏");
		
		User user8 = new User();
		user8.setId(8);
		user8.setUsername("孙悟空");
		
		User user9 = new User();
		user9.setId(9);
		user9.setUsername("猪八戒");
		
		User user10 = new User();
		user10.setId(10);
		user10.setUsername("蜘蛛精");
		
		tree.put("A", user1);
		tree.put("C", user2);
		tree.put("E", user3);
		tree.put("H", user4);
		tree.put("L", user5);
		tree.put("M", user6);
		tree.put("P", user7);
		tree.put("R", user8);
		tree.put("S", user9);
		tree.put("X", user10);
	}
	
	@Test
	public void test() {
		System.out.println(tree.height());
		System.out.println(tree.keys());
		tree.delete("X");
		tree.delete("S");
		tree.delete("R");
		tree.delete("P");
		tree.delete("M");
		tree.delete("L");
		
		System.out.println(tree.height());
		System.out.println(tree.keys());
		System.out.println("min:" + tree.min());
		System.out.println("max:" + tree.max());
	}
}
