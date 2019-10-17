package xyz.guqing.graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Digraph<T extends Comparable<T>> {
	// 顶点总数
	private int nodeCount;
	// 边的总数
	private int edgeCount;
	// 图中的所有边
	private List<Edge<T>> edges;
	// 邻接表,adj.get(v)从顶点v指出的所有节点
	private Map<Node<T>, List<Node<T>>> adj = new HashMap<>();
	
	public static class Node<T extends Comparable<T>> implements Comparable<Node<T>>{
		private T value;
		private int weight = 0;

		public Node(T value) {
			this.value = value;
		}

		public Node(T value, int weight) {
			this.value = value;
			this.weight = weight;
		}

		public Node(Node<T> node) {
			this(node.value, node.weight);
		}

		public T getValue() {
			return value;
		}

		public void setValue(T value) {
			this.value = value;
		}

		public int getWeight() {
			return weight;
		}

		public void setWeight(int weight) {
			this.weight = weight;
		}

		@Override
		public String toString() {
			return "Node[" + value + "]";
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((value == null) ? 0 : value.hashCode());
			result = prime * result + weight;
			return result;
		}

		@Override
		@SuppressWarnings("unchecked")
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if (obj == null) {
				return false;
			}
			if (getClass() != obj.getClass()) {
				return false;
			}

			Node<T> other = (Node<T>) obj;
			if (value == null) {
				if (other.value != null) {
					return false;
				}
			} else if (!value.equals(other.value)) {
				return false;
			}
			if (weight != other.weight) {
				return false;
			}

			return true;
		}

		/**
		 * 先按照节点值排序,相等在按照weight排序
		 */
		@Override
		public int compareTo(Node<T> o) {
			final int cmp = this.value.compareTo(o.value);
			if(cmp != 0) {
				return cmp;
			}
			
			if (this.weight < o.weight) {
				return -1;
			}
            if (this.weight > o.weight) {
            	return 1;
            }
			return 0;
		}
	}

	public static class Edge<T extends Comparable<T>> implements Comparable<Edge<T>>{
		// 边的起始顶点
		private Node<T> from;
		// 边的终止顶点
		private Node<T> to;
		// 边的权重
		private double weight = 0.0;

		public Edge(Node<T> from, Node<T> to) {
			this.from = from;
			this.to = to;
		}

		public Edge(T from, T to, double weight) {
			this.from = new Node<>(from);
			this.to = new Node<>(to);
			this.weight = weight;
		}

		public Edge(Node<T> from, Node<T> to, double weight) {
			this.from = from;
			this.to = to;
			this.weight = weight;
		}

		public Node<T> getFrom() {
			return from;
		}

		public void setFrom(Node<T> from) {
			this.from = from;
		}

		public Node<T> getTo() {
			return to;
		}

		public void setTo(Node<T> to) {
			this.to = to;
		}

		public double getWeight() {
			return weight;
		}

		public void setWeight(double weight) {
			this.weight = weight;
		}

		@Override
		public String toString() {
			return "Edge [" + from.getValue() + "->" + to.getValue() + ", weight=" + weight + "]";
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((from == null) ? 0 : from.hashCode());
			result = prime * result + ((to == null) ? 0 : to.hashCode());
			long temp;
			temp = Double.doubleToLongBits(weight);
			result = prime * result + (int) (temp ^ (temp >>> 32));
			return result;
		}

		@Override
		@SuppressWarnings("unchecked")
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}

			if (obj == null) {
				return false;
			}

			if (getClass() != obj.getClass()) {
				return false;
			}
			
			Edge<T> other = (Edge<T>) obj;
			
			if (from == null) {
				if (other.from != null) {
					return false;
				}
			} else if (!from.equals(other.from)) {
				return false;
			}
			if (to == null) {
				if (other.to != null) {
					return false;
				}
			} else if (!to.equals(other.to)) {
				return false;
			}
			if (Double.doubleToLongBits(weight) != Double.doubleToLongBits(other.weight)) {
				return false;
			}
			return true;
		}

		/**
		 * 先按照from顶点排序,如果相等按照to顶点排序,否则按照权重weight排序
		 */
		@Override
		public int compareTo(Edge<T> e) {
			final int fromCmp = this.from.compareTo(e.from);
			if(fromCmp != 0) {
				return fromCmp;
			}
			
			final int toCmp = this.to.compareTo(e.to);
			if (toCmp != 0) {
				return toCmp;
			}
			
			if (this.weight < e.weight) {
				return -1;
			}
			
            if (this.weight > e.weight) {
            	return 1;
            }
			return 0;
		}
	}

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
			
			List<Node<T>> fromEdgeSet = adj.get(from);
			if (fromEdgeSet == null) {
				fromEdgeSet = new ArrayList<>();
				fromEdgeSet.add(to);
				adj.put(from, fromEdgeSet);
			} else {
				fromEdgeSet.add(to);
			}

			// 保证节点不遗漏
			List<Node<T>> toEdgeSet = adj.get(to);
			if (toEdgeSet == null) {
				toEdgeSet = new ArrayList<>();
				adj.put(to, toEdgeSet);
			}
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
