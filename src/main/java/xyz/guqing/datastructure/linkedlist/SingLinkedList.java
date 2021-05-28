package xyz.guqing.datastructure.linkedlist;


/**
 * 单链表管理Node
 * @author guqing
 *
 */
public class SingLinkedList<T> {
	// 初始化一个头节点，头节点不能动,不存放具体的数据
	private Node<T> head = new Node<>(null);
	private transient int size = 0;
	// 添加节点
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
		// 当推出while循环时，temp就指向了链表的最后
		pointer.next = node;
		size++;
	}
	
	public void addAll(SingLinkedList<T> list) {
		Node<T> pointer = head.next;
		while(pointer.next != null) {
			pointer = pointer.next;
		}
		// 追加在链表尾
		pointer.next = list.head.next;
		size = size + list.size();
	}
	
	public void remove(int index) {
		if(index > size() - 1) {
			throw new RuntimeException("Array Index Out of Bounds.");
		}
		
		int count = 0;
		// 前驱指针
		Node<T> prevPointer = head;
		// 后继指针
		Node<T> nextPointer = prevPointer.next;
		while(nextPointer != null) {
			if(count == index) {
				prevPointer.next = nextPointer.next;
				nextPointer.next = null;
				// 更新链表长度
				size--;
				return;
			}
			count++;
			prevPointer = nextPointer;
			nextPointer = nextPointer.next;
		}
	}
	
	public void remove(T t) {
		// 头节点不能动，需要辅助指针
		// 前驱指针
		Node<T> prevPointer = head;
		// 后继指针
		Node<T> nextPointer = prevPointer.next;
		while(nextPointer != null) {
			if(nextPointer.data == t) {
				// 删除元素
				System.out.println("删除元素");
				prevPointer.next = nextPointer.next;
				nextPointer.next = null;
				size--;
				return;
			}
			prevPointer = nextPointer;
			nextPointer = nextPointer.next;
		}
	}
	
	public T get(int index) {
		if(index < 0 || index > size() - 1) {
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
	
	/**
	 * 迭代头插法逆向链表,遍历一个逆向一个
	 */
	public void reverse() {
		 //如果链表为空或只有一个元素直接返回
        if(head.next == null || head.next.next == null){
            return;
        }
        // 新的头节点，让其next指向null
        Node<T> newHead = new Node<T>(null);
        newHead.next = null;
        
        // 用于临时保存head的next指向
        Node<T> temp = null;
        while(head != null){
        	// 临时保存head的next指向
        	temp = head.next;
        	
        	// 头部插入，先让head.next指向newHead.next,在链接头部和新节点
        	head.next = newHead.next;
        	newHead.next = head;
        	
        	// 让head重新指向temp,达到向后移动遍历的目的
        	head = temp;
        }
        
        // 这里需要将head重新指向新的head处，否则head指向null
        head = newHead;
	}
	
	
	/**
	 * 指针的next指向逆向法反转链表
	 */
	public void reverse1() {
		 //如果链表为空或只有一个元素直接返回
        if(head.next == null || head.next.next == null){
            return;
        }
        // 使用一个新head指行原head指向的位置，这样方便修改原head的指向
        Node<T> newHead = head;
        // 指向head第一个有数据的Node
        Node<T> prevPointer = newHead.next;
        // 指向prevPointer的下一个指针，因此如果数据都没有两个是不需要逆序的
        Node<T> nextPointer = prevPointer.next;
        // 临时指针
        Node<T> temp = null;
        while(nextPointer != null){
        	temp = nextPointer.next;
        	nextPointer.next = prevPointer;
        	prevPointer = nextPointer;
            nextPointer = temp;
        }
        //设置链表尾
        newHead.next.next = null;
        //修改链表头
        newHead.next = prevPointer;
        head = newHead;
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
	 * Node类用于定义链表的基本结构包括两个域：数据域和指针域
	 * @author guqing
	 * @param <T> 数据类型
	 */
	private static class Node<T> {
		final T data;
		Node<T> next;
		
		public Node(T data) {
			super();
			this.data = data;
		}

		@Override
		public String toString() {
			return "Node [data=" + data + ", next=" + next + "]";
		}
	}

	public static void main(String[] args) {
		SingLinkedList<User> userList = new SingLinkedList<>();
		SingLinkedList<User> userList2 = new SingLinkedList<>();
		User user1 = new User();
		user1.setId(1);
		user1.setUsername("zhangsan");
		user1.setNickname("张三");
		user1.setGender("男");
		userList.add(user1);
		userList2.add(user1);
		
		User user2 = new User();
		user2.setId(2);
		user2.setUsername("lisi");
		user2.setNickname("李四");
		user2.setGender("男");
		userList.add(user2);
		userList2.add(user2);
		
		User user3 = new User();
		user3.setId(3);
		user3.setUsername("cuihua");
		user3.setNickname("翠花");
		user3.setGender("女");
		userList.add(user3);
		System.out.println(userList.size);
//		System.out.println("size:" + userList.size());
//		System.out.println(userList);
//		System.out.println(userList.get(userList.size() - 1));
//		userList.reverse();
		userList.addAll(userList2);
		System.out.println(userList);
		System.out.println(userList2);
	}
}
