package xyz.guqing.graph.linked;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import xyz.guqing.graph.linked.Graph.Edge;
import xyz.guqing.graph.linked.Graph.TYPE;
import xyz.guqing.graph.linked.Graph.Node;


public class GraphTest {
	private List<Topo<Integer>> topoList = new ArrayList<>();
	
	@Before
	public void initEdges() {
		Topo<Integer> edge1 = new Topo<>(0, 1, 234);
		Topo<Integer> edge2 = new Topo<>(0, 2, 543);
		Topo<Integer> edge3 = new Topo<>(2, 3, 653);
		Topo<Integer> edge4 = new Topo<>(3, 4, 783);
		Topo<Integer> edge5 = new Topo<>(4, 0, 546);
		
		topoList.add(edge1);
		topoList.add(edge2);
		topoList.add(edge3);
		topoList.add(edge4);
		topoList.add(edge5);
	}
	
	@Test
	public void test() {
		// 创建有向图
		Graph<Integer> graph = buildGraph();
		Node<Integer> from = new Node<>(0);
		Node<Integer> to = new Node<>(4);
		
//		System.out.println(Dijkstra.getShortestPath(graph, from, to));
		
		for(Edge<Integer> edge : graph.getEdges()) {
			System.out.println(edge);
		}
		
		for(Node<Integer> node : graph.getNodes()) {
			System.out.println(node);
		}
		
	}
	
	private Graph<Integer> buildGraph() {
		Set<Edge<Integer>> edges = new HashSet<>();
		Set<Node<Integer>> nodes = new HashSet<>();
		
		topoList.forEach(topo -> {
			Node<Integer> from = new Node<>(topo.getSource());
			Node<Integer> to = new Node<>(topo.getTarget());
			Edge<Integer> edge = new Edge<>(topo.getCost(), from, to);
			
			nodes.add(from);
			nodes.add(to);
			edges.add(edge);
		});
		
		return new Graph<>(TYPE.DIRECTED,nodes, edges);
	}
	
	// Directed
    private Graph<Integer> buildDirectedGraph() {
    	final List<Node<Integer>> verticies = new ArrayList<Node<Integer>>();
        final Graph.Node<Integer> v1 = new Graph.Node<Integer>(1);
        final Graph.Node<Integer> v2 = new Graph.Node<Integer>(2);
        final Graph.Node<Integer> v3 = new Graph.Node<Integer>(3);
        final Graph.Node<Integer> v4 = new Graph.Node<Integer>(4);
        final Graph.Node<Integer> v5 = new Graph.Node<Integer>(5);
        final Graph.Node<Integer> v6 = new Graph.Node<Integer>(6);
        final Graph.Node<Integer> v7 = new Graph.Node<Integer>(7);
        final Graph.Node<Integer> v8 = new Graph.Node<Integer>(8);
        {
            verticies.add(v1);
            verticies.add(v2);
            verticies.add(v3);
            verticies.add(v4);
            verticies.add(v5);
            verticies.add(v6);
            verticies.add(v7);
            verticies.add(v8);
        }

        final List<Edge<Integer>> edges = new ArrayList<Edge<Integer>>();
        final Graph.Edge<Integer> e1_2 = new Graph.Edge<Integer>(7, v1, v2);
        final Graph.Edge<Integer> e1_3 = new Graph.Edge<Integer>(9, v1, v3);
        final Graph.Edge<Integer> e1_6 = new Graph.Edge<Integer>(14, v1, v6);
        final Graph.Edge<Integer> e2_3 = new Graph.Edge<Integer>(10, v2, v3);
        final Graph.Edge<Integer> e2_4 = new Graph.Edge<Integer>(15, v2, v4);
        final Graph.Edge<Integer> e3_4 = new Graph.Edge<Integer>(11, v3, v4);
        final Graph.Edge<Integer> e3_6 = new Graph.Edge<Integer>(2, v3, v6);
        final Graph.Edge<Integer> e6_5 = new Graph.Edge<Integer>(9, v6, v5);
        final Graph.Edge<Integer> e6_8 = new Graph.Edge<Integer>(14, v6, v8);
        final Graph.Edge<Integer> e4_5 = new Graph.Edge<Integer>(6, v4, v5);
        final Graph.Edge<Integer> e4_7 = new Graph.Edge<Integer>(16, v4, v7);
        final Graph.Edge<Integer> e1_8 = new Graph.Edge<Integer>(30, v1, v8);
        {
            edges.add(e1_2);
            edges.add(e1_3);
            edges.add(e1_6);
            edges.add(e2_3);
            edges.add(e2_4);
            edges.add(e3_4);
            edges.add(e3_6);
            edges.add(e6_5);
            edges.add(e6_8);
            edges.add(e4_5);
            edges.add(e4_7);
            edges.add(e1_8);
        }

        Graph<Integer> graph = new Graph<Integer>(Graph.TYPE.DIRECTED, verticies, edges);
        return graph;
    }
}
