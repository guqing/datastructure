package xyz.guqing.datastructure.graph;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import xyz.guqing.datastructure.graph.BreadthFirstDirectedPaths;
import xyz.guqing.datastructure.graph.DepthFirstOrder;
import xyz.guqing.datastructure.graph.structure.Digraph;
import xyz.guqing.datastructure.graph.structure.Edge;
import xyz.guqing.datastructure.graph.structure.Node;
import xyz.guqing.datastructure.graph.DepthFirstDirectedPaths;

public class DigraphTest {
	
	// 测试有向图的基本功能
	@Test
	public void testDigraph() {
		Digraph<Integer> smallGraph = new Digraph<>(getSmallEdgeList());
		Assert.assertEquals(6, smallGraph.getEdgeCount());
		Assert.assertEquals(6, smallGraph.getNodeCount());
		System.out.println(smallGraph.getAdjNodes(new Node<>(0)));
		
		Digraph<Integer> graph = new Digraph<>(getEdgeList());
		Assert.assertEquals(22, graph.getEdgeCount());
		Assert.assertEquals(13, graph.getNodeCount());
	}
	
	// 测试有向图的深度优先遍历
	@Test
	public void testDepthFirstDirectedPaths() {
		Digraph<Integer> graph = new Digraph<>(getEdgeList());
		Node<Integer> s = new Node<>(3);
		DepthFirstDirectedPaths<Integer> dfs = new DepthFirstDirectedPaths<>(graph, s);
		System.out.println("深度优先遍历:");
		System.out.println(dfs);
		/**
		 * 深度优先遍历:
		 * 3 to 0: [3, 5, 4, 2, 0]
		 * 3 to 1: [3, 5, 4, 2, 0, 1]
		 * 3 to 2: [3, 5, 4, 2]
		 * 3 to 3: [3, 3]
		 * 3 to 4: [3, 5, 4]
		 * 3 to 5: [3, 5]
		 * 3 to 6: not connected
		 * 3 to 7: not connected
		 * 3 to 8: not connected
		 * 3 to 9: not connected
		 * 3 to 10: not connected
		 * 3 to 11: not connected
		 * 3 to 12: not connected
		 */
	}
	
	// 测试有向图的广度优先遍历
	@Test
	public void testBreadthFirstDirectedPaths() {
		Digraph<Integer> graph = new Digraph<>(getEdgeList());
		Node<Integer> s = new Node<>(3);
		BreadthFirstDirectedPaths<Integer> bfs = new BreadthFirstDirectedPaths<>(graph, s);
		System.out.println("广度优先遍历:");
		System.out.println(bfs);
		/**
		 * 广度优先遍历:
		 * 3 to 0 (2): [3, 2, 0]
		 * 3 to 1 (3): [3, 2, 0, 1]
		 * 3 to 2 (1): [3, 2]
		 * 3 to 3 (0): [3]
		 * 3 to 4 (2): [3, 5, 4]
		 * 3 to 5 (1): [3, 5]
		 * 3 to 6: not connected
		 * 3 to 7: not connected
		 * 3 to 8: not connected
		 * 3 to 9: not connected
		 * 3 to 10: not connected
		 * 3 to 11: not connected
		 * 3 to 12: not connected
		 */
	}
	
	/**
	 * 创建一组22条边13个点的数据
	 * @return 返回边的list集合
	 */
	private List<Edge<Integer>> getEdgeList() {
		List<Edge<Integer>> edgeList = new ArrayList<>();
		Edge<Integer> eg_4_2 = new Edge<>(4, 2, 0);
		edgeList.add(eg_4_2);
		
		Edge<Integer> eg_2_3 = new Edge<>(2, 3, 0);
		edgeList.add(eg_2_3);
		
		Edge<Integer> eg_3_2 = new Edge<>(3, 2, 0);
		edgeList.add(eg_3_2);
		
		Edge<Integer> eg_6_0 = new Edge<>(6, 0, 0);
		edgeList.add(eg_6_0);
		
		Edge<Integer> eg_0_1 = new Edge<>(0, 1, 0);
		edgeList.add(eg_0_1);
		
		Edge<Integer> eg_2_0 = new Edge<>(2, 0, 0);
		edgeList.add(eg_2_0);
		
		Edge<Integer> eg_11_12 = new Edge<>(11, 12, 0);
		edgeList.add(eg_11_12);
		
		Edge<Integer> eg_12_9 = new Edge<>(12, 9, 0);
		edgeList.add(eg_12_9);
		
		Edge<Integer> eg_9_10 = new Edge<>(9, 10, 0);
		edgeList.add(eg_9_10);
		
		Edge<Integer> eg_9_11 = new Edge<>(9, 11, 0);
		edgeList.add(eg_9_11);
		
		Edge<Integer> eg_7_9 = new Edge<>(7, 9, 0);
		edgeList.add(eg_7_9);
		
		Edge<Integer> eg_10_12 = new Edge<>(10, 12, 0);
		edgeList.add(eg_10_12);
		
		Edge<Integer> eg_11_4 = new Edge<>(11, 4, 0);
		edgeList.add(eg_11_4);
		
		Edge<Integer> eg_4_3 = new Edge<>(4, 3, 0);
		edgeList.add(eg_4_3);

		Edge<Integer> eg_3_5 = new Edge<>(3, 5, 0);
		edgeList.add(eg_3_5);
		
		Edge<Integer> eg_6_8 = new Edge<>(6, 8, 0);
		edgeList.add(eg_6_8);
		
		Edge<Integer> eg_8_6 = new Edge<>(8, 6, 0);
		edgeList.add(eg_8_6);
		
		Edge<Integer> eg_5_4 = new Edge<>(5, 4, 0);
		edgeList.add(eg_5_4);
		
		Edge<Integer> eg_0_5 = new Edge<>(0, 5, 0);
		edgeList.add(eg_0_5);
		
		Edge<Integer> eg_6_4 = new Edge<>(6, 4, 0);
		edgeList.add(eg_6_4);
		
		Edge<Integer> eg_6_9 = new Edge<>(6, 9, 0);
		edgeList.add(eg_6_9);
		
		Edge<Integer> eg_7_6 = new Edge<>(7, 6, 0);
		edgeList.add(eg_7_6);
		
		return edgeList;
	}
	
	/**
	 * 创建一组6条边6个点的数据
	 * @return 返回边的list集合
	 */
	private List<Edge<Integer>> getSmallEdgeList() { 
		List<Edge<Integer>> edgeList = new ArrayList<>();
		Edge<Integer> edge1 = new Edge<>(0, 10, 0.0234); // 0 10 20 3 4 5
		Edge<Integer> edge2 = new Edge<>(10, 20, 0.232);
		Edge<Integer> edge3 = new Edge<>(0, 3, 0.3);
		Edge<Integer> edge4 = new Edge<>(3, 4, 0.345);
		Edge<Integer> edge5 = new Edge<>(4, 5, 0.167);
		Edge<Integer> edge6 = new Edge<>(10, 4, 0.167);

		edgeList.add(edge1);
		edgeList.add(edge2);
		edgeList.add(edge3);
		edgeList.add(edge4);
		edgeList.add(edge5);
		edgeList.add(edge6);
		
		return edgeList;
	}
	
	@Test
	public void test() {
		Digraph<Integer> graph = new Digraph<>(getOtherEdgeList());
		System.out.println(graph.getAdjNodes(new Node<>(4)));
		DepthFirstOrder<Integer> dfs = new DepthFirstOrder<>(graph);
		System.out.println(dfs.pre(new Node<>(12)));
//		System.out.println("   v  pre post");
//		System.out.println("--------------");
//		for (Node<Integer> v : graph.getNodes()) {
//			System.out.printf(v + ": pre=" + dfs.pre(v) + ", post=" + dfs.post(v) + "\n");
//		}
	}
	
	private List<Edge<Integer>> getOtherEdgeList() {
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
