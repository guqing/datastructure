package xyz.guqing.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import xyz.guqing.graph.structure.Digraph;
import xyz.guqing.graph.structure.Edge;
import xyz.guqing.graph.structure.Node;
import xyz.guqing.queue.Queue;

/**
 * 深度优先遍历的顺序
 * @author guqing
 * @param <T> 泛型<T>表示图存储数据的类型
 */
public class DepthFirstOrder<T extends Comparable<T>> {
	// marked[v] = has v been marked in dfs?
	private Set<Node<T>> marked;
	// pre[v] = preorder number of v
	private Map<Node<T>, Integer> pre;
	// post[v] = postorder number of v
	private Map<Node<T>, Integer> post;
	// vertices in preorder
	private Queue<Node<T>> preorder;
	// vertices in postorder
	private Queue<Node<T>> postorder;
	// counter or preorder numbering
	private int preCounter;
	// counter for postorder numbering
	private int postCounter;

	/**
	 * Determines a depth-first order for the digraph {@code G}.
	 * 
	 * @param graph
	 *            the digraph
	 */
	public DepthFirstOrder(Digraph<T> graph) {
		pre = new HashMap<>();
		post = new HashMap<>();
		postorder = new Queue<>();
		preorder = new Queue<>();
		marked = new HashSet<>();
		
		for (Node<T> v : graph.getNodes())
			if (!marked.contains(v)) {
				dfs(graph, v);
			}

		assert check();
	}

	// run DFS in digraph G from vertex v and compute preorder/postorder
	private void dfs(Digraph<T> graph, Node<T> v) {
		marked.add(v);
		pre.put(v, preCounter++);
		preorder.enqueue(v);
		for (Node<T> w : graph.getAdjNodes(v)) {
			if (!marked.contains(w)) {
				dfs(graph, w);
			}
		}
		postorder.enqueue(v);
		post.put(v, postCounter++);
	}

	/**
	 * Returns the preorder number of vertex {@code v}.
	 * 
	 * @param v
	 *            the vertex
	 * @return the preorder number of vertex {@code v}
	 * @throws IllegalArgumentException
	 *             unless {@code 0 <= v < V}
	 */
	public int pre(Node<T> v) {
		return pre.get(v);
	}

	/**
	 * Returns the postorder number of vertex {@code v}.
	 * 
	 * @param v
	 *            the vertex
	 * @return the postorder number of vertex {@code v}
	 * @throws IllegalArgumentException
	 *             unless {@code 0 <= v < V}
	 */
	public int post(Node<T> v) {
		return post.get(v);
	}

	/**
	 * Returns the vertices in postorder.
	 * 
	 * @return the vertices in postorder, as an iterable of vertices
	 */
	public Iterable<Node<T>> post() {
		return postorder;
	}

	/**
	 * Returns the vertices in preorder.
	 * 
	 * @return the vertices in preorder, as an iterable of vertices
	 */
	public Iterable<Node<T>> pre() {
		return preorder;
	}

	/**
	 * Returns the vertices in reverse postorder.
	 * 
	 * @return the vertices in reverse postorder, as an iterable of vertices
	 */
	public Iterable<Node<T>> reversePost() {
		LinkedList<Node<T>> reverse = new LinkedList<>();
		for (Node<T> v : postorder) {
			reverse.addFirst(v);
		}
		return reverse;
	}

	// check that pre() and post() are consistent with pre(v) and post(v)
	private boolean check() {

		// check that post(v) is consistent with post()
		int r = 0;
		for (Node<T> v : post()) {
			if (post(v) != r) {
				System.out.println("post(v) and post() inconsistent");
				return false;
			}
			r++;
		}

		// check that pre(v) is consistent with pre()
		r = 0;
		for (Node<T> v : pre()) {
			if (pre(v) != r) {
				System.out.println("pre(v) and pre() inconsistent");
				return false;
			}
			r++;
		}

		return true;
	}


	/**
	 * Unit tests the {@code DepthFirstOrder} data type.
	 *
	 * @param args
	 *            the command-line arguments
	 */
	public static void main(String[] args) {
		Digraph<Integer> graph = new Digraph<>(getEdgeList());

		DepthFirstOrder<Integer> dfs = new DepthFirstOrder<>(graph);
		System.out.println("   v  pre post");
		System.out.println("--------------");
		for (Node<Integer> v : graph.getNodes()) {
			System.out.printf(v + ": pre=" + dfs.pre(v) + ", post=" + dfs.post(v) + "\n");
		}

		System.out.print("Preorder:  ");
		for (Node<Integer> v : dfs.pre()) {
			System.out.print(v.getValue() + " ");
		}
		System.out.println();
		
		System.out.print("Postorder: ");
		for (Node<Integer> v : dfs.post()) {
			System.out.print(v.getValue() + " ");
		}
		System.out.println();
		
		System.out.print("Reverse postorder: ");
		for (Node<Integer> v : dfs.reversePost()) {
			System.out.print(v.getValue() + " ");
		}
		
		
		/**
		 *	   v  pre post
		 *  --------------
		 *     0    0    8
		 *     1    3    2
		 *     2    9   10
		 *     3   10    9
		 *     4    2    0
		 *     5    1    1
		 *     6    4    7
		 *     7   11   11
		 *     8   12   12
		 *     9    5    6
		 *    10    8    5
		 *    11    6    4
		 *    12    7    3
		 *  Preorder:  0 5 4 1 6 9 11 12 10 2 3 7 8 
		 *  Postorder: 4 5 1 12 11 10 9 6 0 3 2 7 8 
		 *  Reverse postorder: 8 7 2 3 0 6 9 10 11 12 1 5 4 
		 */
	}
	
	private static List<Edge<Integer>> getEdgeList() {
		List<Edge<Integer>> edgeList = new ArrayList<>();
		Edge<Integer> eg_2_3 = new Edge<>(2, 3, 0);
		Edge<Integer> eg_0_6 = new Edge<>(0, 6, 0);
		Edge<Integer> eg_0_1 = new Edge<>(0, 1, 0);
		Edge<Integer> eg_2_0 = new Edge<>(2, 0, 0);
		Edge<Integer> eg_11_12 = new Edge<>(11, 12, 0);
		Edge<Integer> eg_9_12 = new Edge<>(9, 12, 0);
		Edge<Integer> eg_9_10 = new Edge<>(9, 10, 0);
		Edge<Integer> eg_9_11 = new Edge<>(9, 11, 0);
		Edge<Integer> eg_3_5 = new Edge<>(3, 5, 0);
		Edge<Integer> eg_8_7 = new Edge<>(8, 7, 0);
		Edge<Integer> eg_5_4 = new Edge<>(5, 4, 0);
		Edge<Integer> eg_0_5 = new Edge<>(0, 5, 0);
		Edge<Integer> eg_6_4 = new Edge<>(6, 4, 0);
		Edge<Integer> eg_6_9 = new Edge<>(6, 9, 0);
		Edge<Integer> eg_7_6 = new Edge<>(7, 6, 0);
		
		edgeList.add(eg_2_3);
		edgeList.add(eg_0_6);
		edgeList.add(eg_0_1);
		edgeList.add(eg_2_0);
		edgeList.add(eg_11_12);
		edgeList.add(eg_9_12);
		edgeList.add(eg_9_10);
		edgeList.add(eg_9_11);
		edgeList.add(eg_3_5);
		edgeList.add(eg_8_7);
		edgeList.add(eg_5_4);
		edgeList.add(eg_0_5);
		edgeList.add(eg_6_4);
		edgeList.add(eg_6_9);
		edgeList.add(eg_7_6);
		
		return edgeList;
	}

}
