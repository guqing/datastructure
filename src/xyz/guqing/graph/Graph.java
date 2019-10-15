package xyz.guqing.graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// class to represent a graph object
public class Graph<T> {
	// A list of lists to represent adjacency list
	List<List<Node<T>>> adj = new ArrayList<>();
	
	// data structure to store graph edges
	private static class Edge<T> {
		T src;
		T dest;
		int weight;

		Edge(T src, T dest, int weight) {
			this.src = src;
			this.dest = dest;
			this.weight = weight;
		}
	};

	// data structure for adjacency list node
	private static class Node<T> {
		T value;
		int weight;

		Node(T value, int weight) {
			this.value = value;
			this.weight = weight;
		}
	};

	// Constructor to construct graph
	public Graph(List<Edge<T>> edges) {
		// allocate memory for adjacency list
		for (int i = 0; i < edges.size(); i++)
			adj.add(i, new ArrayList<>());

		// add edges to the undirected graph
		for (Edge<T> e : edges) {
			// allocate new node in adjacency List from src to dest
			adj.get(adj.indexOf(e.src)).add(new Node<>(e.dest, e.weight));

			// Uncomment line 39 for undirected graph

			// allocate new node in adjacency List from dest to src
			// adj.get(e.dest).add(new Node(e.src, e.weight));
		}
	}

	// print adjacency list representation of graph
	private void printGraph(Graph<T> graph) {
		int src = 0;
		int n = graph.adj.size();

		while (src < n) {
			// print current vertex and all its neighboring vertices
			for (Node<T> edge : graph.adj.get(src)) {
				System.out.print(src + " --> " + edge.value + " (" + edge.weight + ")\t");
			}

			System.out.println();
			src++;
		}
	}

	// Weighted Directed Graph Implementation in Java
	public static void main(String[] args) {
		// Input: List of edges in a weighted digraph (as per above diagram)
		// tuple (x, y, w) represents an edge from x to y having weight w
		List<Edge<Integer>> edges = Arrays.asList(new Edge<>(0, 1, 6), new Edge<>(1, 2, 7), new Edge<>(2, 0, 5), new Edge<>(2, 1, 4),
				new Edge<>(3, 2, 10), new Edge<>(4, 5, 1), new Edge<>(5, 4, 3));

		// construct graph from given list of edges
		Graph<Integer> graph = new Graph<>(edges);
		// print adjacency list representation of the graph
		graph.printGraph(graph);
	}
}