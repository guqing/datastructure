package xyz.guqing.linkedlist;

/**
 * 双向链表代码实现:
 * void add(T t)
 * void addAll(DoubleLinkedList list)
 * void remove(T t)
 * void remove(int index)
 * T get(int index)
 * int size()
 * String toString()
 * 
 * @author guqing
 */
public class DoubleLinkedList<T> {
	private Node<T> head = new Node<>(null);
	private int size = 0;
	
	public void add(T t) {
		/**
		 * 当不考虑顺序时，找到最后一个节点,让最后一个节点的next指向新节点
		 */
		// 创建数据域节点
		Node<T> node = new Node<>(t);
		// 指针
		Node<T> pointer = head;
		while(pointer.next != null) {
			pointer = pointer.next;
		}
		// 当退出while循环时，pointer就指向了链表的最后
		pointer.next = node;
		node.prev = pointer;
		size++;
	}
	
	public void addAll(DoubleLinkedList<T> list) {
		Node<T> pointer = head.next;
		while(pointer.next != null) {
			pointer = pointer.next;
		}
		// 追加在链表尾
		pointer.next = list.head.next;
		list.head.next.prev = pointer;
		// 更新链表的长度
		size = size + list.size();
	}
	/**
	 * 双向链表可以自我删除，不需要前驱指针
	 * @param t 需要删除的对象
	 */
	public void remove(T t) {
		// 头节点不能动，需要辅助指针
		Node<T> pointer = head.next;
		while(pointer != null) {
			if(pointer.data == t && pointer.next != null) {
				// 删除元素
				pointer.prev.next = pointer.next;
				pointer.next.prev = pointer.prev;
				size--;
				return;
			} else if(pointer.data == t) {
				// 删除最后一个元素
				pointer.prev.next = pointer.next;
				size--;
				return;
			}
			pointer = pointer.next;
		}
	}
	
	public void remove(int index) {
		if(index > size() - 1) {
			throw new RuntimeException("Array Index Out of Bounds.");
		}
		
		int count = 0;
		// 遍历指针
		Node<T> pointer = head.next;
		while(pointer != null) {
			if(count == index && count < size -1) {
				pointer.prev.next = pointer.next;
				pointer.next.prev = pointer.prev;
				// 更新链表长度
				size--;
				return;
			} else if(count == index){
				// 删除最后一个节点
				pointer.prev.next = pointer.next;
				size--;
				return;
			}
			count++;
			pointer = pointer.next;
		}
	}
	
	public T get(int index) {
		if(index > size() - 1) {
			throw new RuntimeException("Array Index Out of Bounds.");
		}
		
		int count = 0;
		Node<T> pointer = head.next;
		while(pointer != null) {
			if(count == index) {
				return pointer.data;
			}
			count++;
			pointer = pointer.next;
		}
		return null;
	}
	
	public int size() {
		return size;
	}
	
	@Override
	public String toString() {
		// 判断链表是否为空
		if(head.next == null) {
			return "[]";
		}
		// 因为头节点不能动，因为我们需要一个指针来遍历
		Node<T> pointer = head.next;
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		while(pointer != null) {
			// 输出节点信息
			sb.append(pointer.data);
			
			// 指针后移
			pointer = pointer.next;
			
			// 添加一个分割符
			if(pointer != null) {
				sb.append(", ");
			}
		}
		sb.append("]");
		return sb.toString();
	}
	
	/**
	 * Node类用于定义双向链表的基本结构包括三个域：数据域和前驱指针域和后继指针域
	 * @author guqing
	 * @param <T> 数据类型
	 */
	private static class Node<T> {
		final T data;
		Node<T> prev;
		Node<T> next;
		
		public Node(T data) {
			super();
			this.data = data;
		}
	}
	
	public static void main(String[] args) {
		DoubleLinkedList<User> userList = new DoubleLinkedList<>();
		
		User user1 = new User();
		user1.setId(1);
		user1.setUsername("zhangsan");
		user1.setNickname("张三");
		user1.setGender("男");
		userList.add(user1);
		
		User user2 = new User();
		user2.setId(2);
		user2.setUsername("lisi");
		user2.setNickname("李四");
		user2.setGender("男");
		userList.add(user2);
		
		User user3 = new User();
		user3.setId(3);
		user3.setUsername("cuihua");
		user3.setNickname("翠花");
		user3.setGender("女");
		userList.add(user3);
		
		userList.remove(user1);
		System.out.println(userList);
	}
}
