package xyz.guqing.graph;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import xyz.guqing.graph.Digraph.Node;
import xyz.guqing.queue.Queue;

/**
 * 图的广度优先遍历
 * 
 * @author guqing
 */
public class BreadthFirstDirectedPaths<T extends Comparable<T>> {
	private static final int INFINITY = Integer.MAX_VALUE;
	// marked.contains(v) = is there an s->v path?
	private Set<Node<T>> marked;
	// edgeTo.get(v) = last edge on shortest s->v path
	private Map<Node<T>, Node<T>> edgeTo;
	// distTo.get(v) = length of shortest s->v path
	private Map<Node<T>, Integer> distTo;
	// breadth traversal graph,only used for the toString() method
	private Digraph<T> graph;
	//breadth traversal source vertex,only used for the toString() method
	private Node<T> s;

	/**
	 * Computes the shortest path from {@code s} and every other vertex in graph
	 * {@code G}.
	 * 
	 * @param G
	 *            the digraph
	 * @param s
	 *            the source vertex
	 * @throws IllegalArgumentException
	 *             unless {@code 0 <= v < V}
	 */
	public BreadthFirstDirectedPaths(Digraph<T> graph, Node<T> s) {
		marked = new HashSet<>();
		distTo = new HashMap<>();
		edgeTo = new HashMap<>();
		this.graph = graph;
		this.s = s;
		for (Node<T> v : graph.getNodes()) {
			distTo.put(v, INFINITY);
		}
		bfs(graph, s);
	}

	/**
	 * Computes the shortest path from any one of the source vertices in
	 * {@code sources} to every other vertex in graph {@code G}.
	 * 
	 * @param G
	 *            the digraph
	 * @param sources
	 *            the source vertices
	 * @throws IllegalArgumentException
	 *             unless each vertex {@code v} in {@code sources} satisfies
	 *             {@code 0 <= v < V}
	 */
	public BreadthFirstDirectedPaths(Digraph<T> graph, Iterable<Node<T>> sources) {
		marked = new HashSet<>();
		distTo = new HashMap<>();
		edgeTo = new HashMap<>();
		this.graph = graph;
		for (Node<T> v : graph.getNodes()) {
			distTo.put(v, INFINITY);
		}
		bfs(graph, sources);
	}

	// BFS from single source
	private void bfs(Digraph<T> graph, Node<T> s) {
		Queue<Node<T>> queue = new Queue<>();
		marked.add(s);
		distTo.put(s, 0);
		queue.enqueue(s);
		while (!queue.isEmpty()) {
			Node<T> v = queue.dequeue();
			for (Node<T> w : graph.getAdjNodes(v)) {
				if (!marked.contains(w)) {
					edgeTo.put(w, v);
					distTo.put(w, distTo.get(v) + 1);
					marked.add(w);
					queue.enqueue(w);
				}
			}
		}
	}

	// BFS from multiple sources
	private void bfs(Digraph<T> graph, Iterable<Node<T>> sources) {
		Queue<Node<T>> q = new Queue<>();
		for (Node<T> s : sources) {
			marked.add(s);
			distTo.put(s, 0);
			q.enqueue(s);
		}
		while (!q.isEmpty()) {
			Node<T> v = q.dequeue();
			for (Node<T> w : graph.getAdjNodes(v)) {
				if (!marked.contains(w)) {
					edgeTo.put(w, v);
					distTo.put(w, distTo.get(v) + 1);
					marked.add(w);
					q.enqueue(w);
				}
			}
		}
	}

	/**
	 * Is there a directed path from the source {@code s} (or sources) to vertex
	 * {@code v}?
	 * 
	 * @param v
	 *            the vertex
	 * @return {@code true} if there is a directed path, {@code false} otherwise
	 * @throws IllegalArgumentException
	 *             unless {@code 0 <= v < V}
	 */
	public boolean hasPathTo(Node<T> v) {
		return marked.contains(v);
	}

	/**
	 * Returns the number of edges in a shortest path from the source {@code s}
	 * (or sources) to vertex {@code v}?
	 * 
	 * @param v
	 *            the vertex
	 * @return the number of edges in a shortest path
	 * @throws IllegalArgumentException
	 *             unless {@code 0 <= v < V}
	 */
	public int distTo(Node<T> v) {
		return distTo.get(v);
	}

	/**
	 * Returns a shortest path from {@code s} (or sources) to {@code v}, or
	 * {@code null} if no such path.
	 * 
	 * @param v
	 *            the vertex
	 * @return the sequence of vertices on a shortest path, as an Iterable
	 * @throws IllegalArgumentException
	 *             unless {@code 0 <= v < V}
	 */
	public Iterable<Node<T>> pathTo(Node<T> v) {
		if (!hasPathTo(v)) {
			return null;
		}
		Stack<Node<T>> path = new Stack<>();
		Node<T> x;
		for (x = v; distTo.get(x) != null && distTo.get(x) != 0; x = edgeTo.get(x)) {
			path.push(x);
		}
		path.push(x);
		return path;
	}

	// 输出起点s到各个顶点之间的路径
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (Node<T> v : graph.getSortedNodes()) {
			if (hasPathTo(v)) {
				LinkedList<T> pathList = new LinkedList<>();
				for (Node<T> x : pathTo(v)) {
					if (x == s) {
						pathList.addFirst(x.getValue());
					} else {
						pathList.addFirst(x.getValue());
					}
				}
				sb.append(s.getValue())
					.append(" to ")
					.append(v.getValue())
					.append(" (")
					.append(distTo(v))
					.append("): ")
					.append(pathList)
					.append("\n");
			} else {
				sb.append(s.getValue()).append(" to ").append(v.getValue()).append(": not connected\n");
			}
		}

		return sb.toString();
	}
}