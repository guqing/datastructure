package xyz.guqing.graph;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import xyz.guqing.graph.Digraph.Node;

/**
 * 有向图的深度优先路径搜索
 * 
 * @author guqing
 * @param <T>
 *            泛型<T>表示图存储的数据类型
 */
public class DepthFirstDirectedPaths<T extends Comparable<T>> {
	// Save the graph to output the search results
	private Digraph<T> graph;
	// marked.contains(v) = true if v is reachable from s
	private Set<Node<T>> marked;
	// edgeTo.get(v) = last edge on path from s to v
	private Map<Node<T>, Node<T>> edgeTo;
	// source vertex
	private final Node<T> s;

	/**
	 * Computes a directed path from {@code s} to every other vertex in digraph
	 * {@code G}.
	 * 
	 * @param graph
	 *            the digraph
	 * @param s
	 *            the source vertex
	 * @throws IllegalArgumentException
	 *             unless {@code 0 <= s < V}
	 */
	public DepthFirstDirectedPaths(Digraph<T> graph, Node<T> s) {
		marked = new HashSet<>();
		edgeTo = new HashMap<>();
		this.s = s;
		this.graph = graph;
		dfs(graph, s);
	}

	private void dfs(Digraph<T> graph, Node<T> v) {
		marked.add(v);
		for (Node<T> w : graph.getAdjNodes(v)) {
			if (!marked.contains(w)) {
				edgeTo.put(w, v);
				dfs(graph, w);
			}
		}
	}

	/**
	 * Is there a directed path from the source vertex {@code s} to vertex
	 * {@code v}?
	 * 
	 * @param v
	 *            the vertex
	 * @return {@code true} if there is a directed path from the source vertex
	 *         {@code s} to vertex {@code v}, {@code false} otherwise
	 * @throws IllegalArgumentException
	 *             unless {@code 0 <= v < V}
	 */
	public boolean hasPathTo(Node<T> v) {
		return marked.contains(v);
	}

	/**
	 * Returns a directed path from the source vertex {@code s} to vertex
	 * {@code v}, or {@code null} if no such path.
	 * 
	 * @param v
	 *            the vertex
	 * @return the sequence of vertices on a directed path from the source
	 *         vertex {@code s} to vertex {@code v}, as an Iterable
	 * @throws IllegalArgumentException
	 *             unless {@code 0 <= v < V}
	 */
	public Iterable<Node<T>> pathTo(Node<T> v) {
		if (!hasPathTo(v)) {
			return null;
		}
		Stack<Node<T>> path = new Stack<>();
		for (Node<T> x = v; x != s && x != null; x = edgeTo.get(x)) {
			path.push(x);
		}
		path.push(s);
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
				sb.append(s.getValue()).append(" to ").append(v.getValue()).append(": ").append(pathList).append("\n");
			} else {
				sb.append(s.getValue()).append(" to ").append(v.getValue()).append(": not connected\n");
			}
		}

		return sb.toString();
	}
}