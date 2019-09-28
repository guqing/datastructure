package xyz.guqing.tree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HuffmanCode {
	private Node root;
	private Map<Byte, String> huffmanCodeTable = new HashMap<>();
	private StringBuilder sb = new StringBuilder();
	
	private static class Node implements Comparable<Node> {
        private Byte ch;
        private int wight;
        private Node left;
        private Node right;

        public Node(Byte ch, int wight) {
            this.ch    = ch;
            this.wight  = wight;
        }

        // is the node a leaf node?
        private boolean isLeaf() {
            assert ((left == null) && (right == null)) || ((left != null) && (right != null));
            return (left == null) && (right == null);
        }

        // compare, based on frequency
        public int compareTo(Node that) {
            return this.wight - that.wight;
        }

		@Override
		public String toString() {
			return "Node [ch=" + ch + ", wight=" + wight + "]";
		}
    }
	
	/**
	 * 哈夫曼编码
	 * @param bytes 字符串的字节数组
	 * @return 返回哈夫曼编码后的字节数组
	 */
	public byte[] encode(byte[] bytes) {
		// 通过bytes数组获取节点list
		List<Node> nodes = getNodes(bytes);
		// 构建哈夫曼树
		root = buildHuffman(nodes);
		// 通过哈夫曼树获取霍夫曼编码表
		getHuffmanCodeTable();
		// 根据哈夫曼编码表获取哈夫曼编码
		return encode(bytes, huffmanCodeTable);
	}

	
	/**
	 * 哈夫曼数据解压
	 * 1.将字节数组转为哈夫曼对应的二进制字符
	 * 2.将二进制字符串对照哈夫曼编码恢复成字符串
	 */
	public String decode(byte[] bytes) {
		byte[] byteArray = decode(huffmanCodeTable, bytes);
		return new String(byteArray);
	}
	
	/**
	 * @param huffmanCodeTable 字符串得到的哈夫曼编码表
	 * @param huffermanBytes 哈夫曼编码得到的字节数组
	 * @return
	 */
	private byte[] decode(Map<Byte,String> huffmanCodeTable, byte[] huffermanBytes) {
		// 得到huffermanBytes对应的二进制字符串
		StringBuilder sb = new StringBuilder();
		// 将byte转成二进制的字符串
		for(int i=0; i< huffermanBytes.length; i++) {
			// 判断是否是最后一个字节，最后一个字节不需要补高位
			boolean flag = (i == huffermanBytes.length - 1);
			String byteString = byte2BitString(huffermanBytes[i], !flag);
			sb.append(byteString);
		}
		
		// 把字符串按照指定的哈夫曼编码表进行解码
		Map<String, Byte> map = new HashMap<>();
		for(Map.Entry<Byte, String> entry : huffmanCodeTable.entrySet()) {
			map.put(entry.getValue(), entry.getKey());
		}
		
		// 创建一个集合，存储byte
		List<Byte> list = new ArrayList<>();
		for(int  i = 0; i < sb.length(); ) {
			int count = 1;
			// 取出一个[i, (i+count)]的串去查询哈夫曼编码表
			boolean flag = true;
			while(flag && (i + count) <= sb.length()) {
				String key = sb.substring(i, i + count);
				Byte bit = map.get(key);
				if(bit == null) {//说明没有匹配到
					count++;
				}else {
					list.add(bit);
					//匹配到
					flag = false;
				}
			}
			// i直接移动到current
			i = i + count;
		}
		
		// for循环结束后list中存储的就是解码得到的所有单字符
		byte[] buffer = new byte[list.size()];
		for(int i=0; i<buffer.length; i++) {
			buffer[i] = list.get(i);
		}
		return buffer;
	}
	
	/**
	 * 将1字节转为二进制字符串
	 * @param b 字节
	 * @param flag true表示需要补高位,如果是最后一个字节不需要补高位
	 * @return 返回b对应的二进制字符串(按补码返回)
	 */
	private String byte2BitString(byte b, boolean flag) {
		// 使用变量保存b,相当于转为了int
		int temp = b;
		
		if(flag) {
			// 如果b是正数得到的s只有一位,需要补高位,按位与运算
			temp |= 256;
		}
		
		// 这里得到的是temp对应的二进制的补码
		String s = Integer.toBinaryString(temp);
		
		if(flag) {
			// 大于8位,截取后8位,否则直接返回
			return s.substring(s.length() - 8);
		}
		return s;
	}
	
	/**
	 * @param bytes 原始字符串对应的byte[]
	 * @param huffmanCodes 生成的哈夫曼编码表
	 * @return 返回哈夫曼编码处理后的byte[]
	 */
	private byte[] encode(byte[] bytes, Map<Byte, String> huffmanCodeTable) {
		StringBuilder sb = new StringBuilder();
		for(byte b : bytes) {
			sb.append(huffmanCodeTable.get(b));
		}
		// 将sb对应的字符串转为byte数组
		// length如果是8的整数就等于length否则+1
		int length = 0;
		if(sb.length() % 8 ==0) {
			length = sb.length()/8;
		} else {
			length = sb.length()/8 + 1;
		}
				
		// 创建一个存储压缩后数据的byte数组
		byte[] buffer = new byte[length];
		
		//每8为对应一个byte,所以步长为8
		int index = 0;
		for(int i = 0; i<sb.length(); i+=8) {
			String strByte;
			if(i + 8 > sb.length()) {
				// 不足8位
				strByte = sb.substring(i);
			} else {
				strByte = sb.substring(i, i+8);
			}
			
			// 将strByte转二进制的byte数组放入到buffer
			buffer[index] = (byte) Integer.parseInt(strByte, 2);
			index++;
		}
		
		return buffer;
	}
	
	private void getHuffmanCodeTable() {
		getHuffmanCodeTable(root, "", sb);
	}
	
	private void getHuffmanCodeTable(Node node, String code, StringBuilder sb) {
		// 将哈夫曼编码表存放在Map<Byte, String>中
		// 在生成哈夫曼编码表示，需要去拼接路径，定义一个StringBuilder存储某个叶子节点的路径
		StringBuilder stringBuilder = new StringBuilder(sb);
		stringBuilder.append(code);
		
		if(node != null && !node.isLeaf()) {
			// 非叶子节点，递归处理
			//向左递归
			getHuffmanCodeTable(node.left, "0", stringBuilder);
			//向右递归
			getHuffmanCodeTable(node.right, "1", stringBuilder);
		} else {
			huffmanCodeTable.put(node.ch, stringBuilder.toString());
		}
	}
	
	private Node buildHuffman(List<Node> nodes) {
		while(nodes.size() > 1) {
			// 对集合进行排序
			Collections.sort(nodes);
			
			// 取出根节点权值最小的两棵二叉树(单个节点也可以看成最简单的二叉树)
			Node leftNode = nodes.get(0);
			Node rightNode = nodes.get(1);
			
			// 构建一棵新的二叉树,字符都是存储在叶子节点,所以第一个参数为null
			Node parent = new Node(null, leftNode.wight + rightNode.wight);
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
	
	/**
	 * @param bytes 接收一个字节数组
	 * @return 返回一个Node的List集合
	 */
	private List<Node> getNodes(byte[] bytes) {
		List<Node> nodes = new ArrayList<>();
		
		// 存储每个byte出现的次数
		Map<Byte, Integer> countMap = countCharacter(bytes);
		
		for(Map.Entry<Byte, Integer> entry : countMap.entrySet()) {
			Node node = new Node(entry.getKey(), entry.getValue());
			nodes.add(node);
		}
		
		return nodes;
	}
	
	// 统计字符出现次数
	private Map<Byte, Integer> countCharacter (byte[] bytes) {
		Map<Byte, Integer> map = new HashMap<>();
		
		for(byte s : bytes) {
			Integer count = map.get(s);
			if(count != null) {
				count++;
				map.put(s, count);
			} else {
				map.put(s, 1);
			}
		}
		return map;
	}
	
	public String preorder() {
		List<Integer> list = new ArrayList<>();
		preorder(root, list);
		return list.toString();
	}
	
	private void preorder(Node parent, List<Integer> list) {
		list.add(parent.wight);
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
		String str = "I'm not afraid when the rain won't stop.";
		HuffmanCode huffmanCode = new HuffmanCode();
		byte[] encode = huffmanCode.encode(str.getBytes());
		System.out.println(Arrays.toString(encode));
		System.out.println("解码：");
		System.out.println(huffmanCode.decode(encode));
	}
}
