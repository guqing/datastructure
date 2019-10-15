package xyz.guqing.graph.linked;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import xyz.guqing.graph.linked.Graph.Edge;
import xyz.guqing.graph.linked.Graph.Node;

public class DepthFirstTraversal {

    @SuppressWarnings("unchecked")
    public static <T extends Comparable<T>> Graph.Node<T>[] depthFirstTraversal(Graph<T> graph, Graph.Node<T> source) {
        // use for look-up via index
        final ArrayList<Node<T>> vertices = new ArrayList<Node<T>>();
        vertices.addAll(graph.getNodes());

        // used for look-up via vertex
        final int n = vertices.size();
        final Map<Node<T>,Integer> vertexToIndex = new HashMap<Node<T>,Integer>();
        for (int i=0; i<n; i++) {
            final Node<T> v = vertices.get(i);
            vertexToIndex.put(v,i);
        }

        // adjacency matrix
        final byte[][] adj = new byte[n][n];
        for (int i=0; i<n; i++) {
            final Node<T> v = vertices.get(i);
            final int idx = vertexToIndex.get(v);
            final byte[] array = new byte[n];
            adj[idx] = array;
            final List<Edge<T>> edges = v.getEdges();
            for (Edge<T> e : edges)
                array[vertexToIndex.get(e.getToNode())] = 1;
        }

        // visited array
        final byte[] visited = new byte[n];
        for (int i = 0; i < visited.length; i++)
            visited[i] = -1;

        // for holding results
        final Graph.Node<T>[] arr = new Graph.Node[n];

        // start at the source
        Node<T> element = source;       
        int c = 0;
        int i = vertexToIndex.get(element);
        int k = 0;

        visited[i] = 1;
        arr[k] = element;
        k++;

        final Stack<Node<T>> stack = new Stack<Node<T>>();
        stack.push(source);
        while (!stack.isEmpty()) {    
            element = stack.peek();
            c = vertexToIndex.get(element);
            i = 0;
            while (i < n) {
                if (adj[c][i] == 1 && visited[i] == -1) {
                    final Node<T> v = vertices.get(i);
                    stack.push(v);
                    visited[i] = 1;

                    element = v;
                    c = vertexToIndex.get(element);
                    i = 0;

                    arr[k] = v;
                    k++;
                    continue;
                }
                i++;
            }
            stack.pop();    
        }
        return arr;
    }

    public static int[] depthFirstTraversal(int n, byte[][] adjacencyMatrix, int source) {
        final int[] visited = new int[n];
        for (int i = 0; i < visited.length; i++)
            visited[i] = -1;

        int element = source;
        int i = source;
        int arr[] = new int[n];
        int k = 0;

        visited[source] = 1;
        arr[k] = element;
        k++;

        final Stack<Integer> stack = new Stack<Integer>();   
        stack.push(source);
        while (!stack.isEmpty()) {    
            element = stack.peek();
            i = 0;    
            while (i < n) {
                if (adjacencyMatrix[element][i] == 1 && visited[i] == -1) {
                    stack.push(i);
                    visited[i] = 1;

                    element = i;
                    i = 0;

                    arr[k] = element;
                    k++;                
                    continue;
                }
                i++;
            }
            stack.pop();    
        }
        return arr;
    }
}