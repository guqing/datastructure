package xyz.guqing.datastructure.graph;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

import xyz.guqing.datastructure.graph.structure.Digraph;
import xyz.guqing.datastructure.graph.structure.Edge;
import xyz.guqing.datastructure.graph.AllPathFind;
import xyz.guqing.datastructure.graph.structure.Node;

/**
 * 搜索所有路径测试
 * @author guqing
 */
public class AllPathFindTest {
	private AllPathFind<Integer> pathFind;
	
	@Before
	public void buildPathFind() {
		Digraph<Integer> graph = new Digraph<>(getEdgeList());
		this.pathFind = new AllPathFind<>(graph);
	}
	
	@Test
	public void test1() {
		Node<Integer> start = new Node<>(0);
		Node<Integer> end = new Node<Integer>(2);
		
		List<List<Node<Integer>>> paths = pathFind.findAllPath(start, end);
		// 输出每条路径
		Iterator<List<Node<Integer>>> it = paths.iterator();
		/**
		 * [Node[0], Node[5], Node[4], Node[3], Node[2]
		 * [Node[0], Node[5], Node[4], Node[2]]
		 */
		while(it.hasNext()) {
			System.out.println(it.next());
		}
	}
	
	@Test
	public void test2() {
		Node<Integer> start = new Node<>(6);
		Node<Integer> end = new Node<Integer>(12);
		
		List<List<Node<Integer>>> paths = pathFind.findAllPath(start, end);
		// 输出每条路径
		Iterator<List<Node<Integer>>> it = paths.iterator();
		/**
		 * [Node[6], Node[9], Node[10], Node[12]]
		 * [Node[6], Node[9], Node[11], Node[12]]
		 */
		while(it.hasNext()) {
			System.out.println(it.next());
		}
	}
	
	
	@Test
	public void test3() {
		Node<Integer> start = new Node<>(10);
		Node<Integer> end = new Node<Integer>(3);
		
		List<List<Node<Integer>>> paths = pathFind.findAllPath(start, end);
		// 输出每条路径
		Iterator<List<Node<Integer>>> it = paths.iterator();
		/**
		 * [Node[10], Node[12], Node[9], Node[11], Node[4], Node[3]
		 * [Node[10], Node[12], Node[9], Node[11], Node[4], Node[2], Node[3]]
		 */
		while(it.hasNext()) {
			System.out.println(it.next());
		}
	}
	
	@Test
	public void test4() {
		Node<Integer> start = new Node<>(0);
		Node<Integer> end = new Node<Integer>(1);
		
		List<List<Node<Integer>>> paths = pathFind.findAllPath(start, end);
		// 输出每条路径
		Iterator<List<Node<Integer>>> it = paths.iterator();
		/**
		 * [Node[0], Node[1]]
		 */
		while(it.hasNext()) {
			System.out.println(it.next());
		}
	}
	
	public List<Edge<Integer>> getEdgeList() {
		/**
		 * edge: 22
		 * node: 12
		 * 0: 0->5, 0->1
		 * 1: 
		 * 2: 2->0, 2->3
		 * 3: 3->5, 3->2
		 * 4: 4->3, 4->2
		 * 5: 5->4
		 * 6: 6->0, 6->4, 6->8, 6->9
		 * 7: 7->6, 7->9
		 * 8: 8->6
		 * 9: 9->10, 9->11
		 * 10: 10->12
		 * 11: 11->4, 11->12
		 * 12: 12->9
		 */
		
		List<Edge<Integer>> edgeList = new ArrayList<>();
		Edge<Integer> edge_05 = new Edge<>(0, 5, 0);
		Edge<Integer> edge_01 = new Edge<>(0, 1, 0);
		Edge<Integer> edge_20 = new Edge<>(2, 0, 0);
		Edge<Integer> edge_23 = new Edge<>(2, 3, 0);
		Edge<Integer> edge_35 = new Edge<>(3, 5, 0);
		Edge<Integer> edge_32 = new Edge<>(3, 2, 0);
		Edge<Integer> edge_43 = new Edge<>(4, 3, 0);
		Edge<Integer> edge_42 = new Edge<>(4, 2, 0);
		Edge<Integer> edge_54 = new Edge<>(5, 4, 0);
		Edge<Integer> edge_60 = new Edge<>(6, 0, 0);
		Edge<Integer> edge_64 = new Edge<>(6, 4, 0);
		Edge<Integer> edge_68 = new Edge<>(6, 8, 0);
		Edge<Integer> edge_69 = new Edge<>(6, 9, 0);
		Edge<Integer> edge_76 = new Edge<>(7, 6, 0);
		Edge<Integer> edge_79 = new Edge<>(7, 9, 0);
		Edge<Integer> edge_86 = new Edge<>(8, 6, 0);
		Edge<Integer> edge_9_10 = new Edge<>(9, 10, 0);
		Edge<Integer> edge_9_11 = new Edge<>(9, 11, 0);
		Edge<Integer> edge_10_12 = new Edge<>(10, 12, 0);
		Edge<Integer> edge_11_4 = new Edge<>(11, 4, 0);
		Edge<Integer> edge_11_12 = new Edge<>(11, 12, 0);
		Edge<Integer> edge_12_9 = new Edge<>(12, 9, 0);
		
		edgeList.add(edge_05);
		edgeList.add(edge_01);
		edgeList.add(edge_20);
		edgeList.add(edge_23);
		edgeList.add(edge_35);
		edgeList.add(edge_32);
		edgeList.add(edge_43);
		edgeList.add(edge_42);
		edgeList.add(edge_54);
		edgeList.add(edge_60);
		edgeList.add(edge_64);
		edgeList.add(edge_68);
		edgeList.add(edge_69);
		edgeList.add(edge_76);
		edgeList.add(edge_79);
		edgeList.add(edge_86);
		edgeList.add(edge_9_10);
		edgeList.add(edge_9_11);
		edgeList.add(edge_10_12);
		edgeList.add(edge_11_4);
		edgeList.add(edge_11_12);
		edgeList.add(edge_12_9);
		
		return edgeList;
	}
}
