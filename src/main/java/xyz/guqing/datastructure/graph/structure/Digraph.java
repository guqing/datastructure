package xyz.guqing.datastructure.graph.structure;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 有向图
 * @author guqing
 * @param <T> 泛型<T>表示有向图存储的数据类型
 */
public class Digraph<T extends Comparable<T>> {
	/**
	 * 顶点总数
	 */
	private int nodeCount;
	/**
	 * 边的总数
	 */
	private int edgeCount;
	/**
	 * 图中的所有边
	 */
	private List<Edge<T>> edges;
	/**
	 * 邻接表,adj.get(v)从顶点v指出的所有节点
	 */
	private final Map<Node<T>, List<Node<T>>> adj = new HashMap<>();

	public Digraph() {}

	public Digraph(List<Edge<T>> edges) {
		if (edges == null) {
			throw new IllegalArgumentException("the parameter edges cannot be null");
		}
		this.edges = edges;
		this.edgeCount = edges.size();
		// 设置顶点邻接表
		for (Edge<T> edge : edges) {
			Node<T> from = edge.getFrom();
			Node<T> to = edge.getTo();

			// 从map中获取from对应的出度集合,并将to添加到集合中,集合为null就创建再将to添加到集合中
			adj.computeIfAbsent(from, k -> new ArrayList<>()).add(to);

			// 从map中获取to对应的出度集合,为null就创键集合并以to为key放入map
			adj.computeIfAbsent(to, k -> new ArrayList<>());
		}
		// 设置节点数量
		this.nodeCount = adj.keySet().size();
	}

	/**
	 * Returns graph vertex(also called node) count
	 * @return The number of node in a graph,if has 0 node return 0
	 */
	public int getNodeCount() {
		return this.nodeCount;
	}

	/**
	 * Returns graph edge count
	 * @return The number of edges in a graph
	 */
	public int getEdgeCount() {
		return this.edgeCount;
	}

	/**
	 * Returns a collection of adjacency points of vertex {@code s}
	 * @param s vertex s
	 * @return a collection of adjacency points of vertex {@code s}
	 */
	public List<Node<T>> getAdjNodes(Node<T> s) {
		return adj.get(s);
	}
	
	/**
	 * Returns nodes in digraph
	 * @return nodes in digraph
	 */
	public Set<Node<T>> getNodes() {
		return adj.keySet();
	}
	
	/**
	 * Returns the node collection sorted in natural order
	 * @return node collection if there is a node in digraph,
	 *         otherwise NULL
	 */
	public List<Node<T>> getSortedNodes() {
		List<Node<T>> nodeList = new ArrayList<>(adj.keySet());
		Collections.sort(nodeList);
		return nodeList;
	}
	
	/**
	 * Returns the edge collection in digraph
	 * @return edge collection if there is a edge in digraph,
	 *         otherwise NULL
	 */
	public List<Edge<T>> getEdges() {
		return this.edges;
	}
}
