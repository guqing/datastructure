package xyz.guqing.graph.linked;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * Graph. Could be directed or undirected depending on the TYPE enum. A graph is
 * an abstract representation of a set of objects where some pairs of the
 * objects are connected by links.
 * <p>
 * @see <a href="https://en.wikipedia.org/wiki/Graph_(mathematics)">Graph (Wikipedia)</a>
 * <br>
 * @author Justin Wetherell <phishman3579@gmail.com>
 */
@SuppressWarnings("unchecked")
public class Graph<T extends Comparable<T>> {

    private List<Node<T>> allNodes = new ArrayList<Node<T>>();
    private List<Edge<T>> allEdges = new ArrayList<Edge<T>>();

    public enum TYPE {
        DIRECTED, UNDIRECTED
    }

    /** Defaulted to undirected */
    private TYPE type = TYPE.UNDIRECTED;

    public Graph() { }

    public Graph(TYPE type) {
        this.type = type;
    }

    /** Deep copies **/
    public Graph(Graph<T> g) {
        type = g.getType();

        // Copy the vertices which also copies the edges
        for (Node<T> v : g.getNodes())
            this.allNodes.add(new Node<T>(v));

        for (Node<T> v : this.getNodes()) {
            for (Edge<T> e : v.getEdges()) {
                this.allEdges.add(e);
            }
        }
    }

    /**
     * Creates a Graph from the vertices and edges. This defaults to an undirected Graph
     * 
     * NOTE: Duplicate vertices and edges ARE allowed.
     * NOTE: Copies the vertex and edge objects but does NOT store the Collection parameters itself.
     * 
     * @param vertices Collection of vertices
     * @param edges Collection of edges
     */
    public Graph(Collection<Node<T>> vertices, Collection<Edge<T>> edges) {
        this(TYPE.UNDIRECTED, vertices, edges);
    }

    /**
     * Creates a Graph from the vertices and edges.
     * 
     * NOTE: Duplicate vertices and edges ARE allowed.
     * NOTE: Copies the vertex and edge objects but does NOT store the Collection parameters itself.
     * 
     * @param vertices Collection of vertices
     * @param edges Collection of edges
     */
    public Graph(TYPE type, Collection<Node<T>> vertices, Collection<Edge<T>> edges) {
        this(type);

        this.allNodes.addAll(vertices);
        this.allEdges.addAll(edges);

        for (Edge<T> e : edges) {
            final Node<T> from = e.from;
            final Node<T> to = e.to;

            if (!this.allNodes.contains(from) || !this.allNodes.contains(to))
                continue;

            from.addEdge(e);
            if (this.type == TYPE.UNDIRECTED) {
                Edge<T> reciprical = new Edge<T>(e.cost, to, from);
                to.addEdge(reciprical);
                this.allEdges.add(reciprical);
            }
        }
    }

    public TYPE getType() {
        return type;
    }

    public List<Node<T>> getNodes() {
        return allNodes;
    }

    public List<Edge<T>> getEdges() {
        return allEdges;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int code = this.type.hashCode() + this.allNodes.size() + this.allEdges.size();
        for (Node<T> v : allNodes)
            code *= v.hashCode();
        for (Edge<T> e : allEdges)
            code *= e.hashCode();
        return 31 * code;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object g1) {
        if (!(g1 instanceof Graph))
            return false;

        final Graph<T> g = (Graph<T>) g1;

        final boolean typeEquals = this.type == g.type;
        if (!typeEquals)
            return false;

        final boolean verticesSizeEquals = this.allNodes.size() == g.allNodes.size();
        if (!verticesSizeEquals)
            return false;

        final boolean edgesSizeEquals = this.allEdges.size() == g.allEdges.size();
        if (!edgesSizeEquals)
            return false;

        // Nodes can contain duplicates and appear in different order but both arrays should contain the same elements
        final Object[] ov1 = this.allNodes.toArray();
        Arrays.sort(ov1);
        final Object[] ov2 = g.allNodes.toArray();
        Arrays.sort(ov2);
        for (int i=0; i<ov1.length; i++) {
            final Node<T> v1 = (Node<T>) ov1[i];
            final Node<T> v2 = (Node<T>) ov2[i];
            if (!v1.equals(v2))
                return false;
        }

        // Edges can contain duplicates and appear in different order but both arrays should contain the same elements
        final Object[] oe1 = this.allEdges.toArray();
        Arrays.sort(oe1);
        final Object[] oe2 = g.allEdges.toArray();
        Arrays.sort(oe2);
        for (int i=0; i<oe1.length; i++) {
            final Edge<T> e1 = (Edge<T>) oe1[i];
            final Edge<T> e2 = (Edge<T>) oe2[i];
            if (!e1.equals(e2))
                return false;
        }

        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        for (Node<T> v : allNodes)
            builder.append(v.toString());
        return builder.toString();
    }

    public static class Node<T extends Comparable<T>> implements Comparable<Node<T>> {

        private T value = null;
        private int weight = 0;
        private List<Edge<T>> edges = new ArrayList<Edge<T>>();

        public Node(T value) {
            this.value = value;
        }

        public Node(T value, int weight) {
            this(value);
            this.weight = weight;
        }

        /** Deep copies the edges along with the value and weight **/
        public Node(Node<T> vertex) {
            this(vertex.value, vertex.weight);

            this.edges.addAll(vertex.edges);
        }

        public T getValue() {
            return value;
        }

        public int getWeight() {
            return weight;
        }

        public void setWeight(int weight) {
            this.weight = weight;
        }

        public void addEdge(Edge<T> e) {
            edges.add(e);
        }

        public List<Edge<T>> getEdges() {
            return edges;
        }

        public Edge<T> getEdge(Node<T> v) {
            for (Edge<T> e : edges) {
                if (e.to.equals(v))
                    return e;
            }
            return null;
        }

        public boolean pathTo(Node<T> v) {
            for (Edge<T> e : edges) {
                if (e.to.equals(v))
                    return true;
            }
            return false;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int hashCode() {
            final int code = this.value.hashCode() + this.weight + this.edges.size();
            return 31 * code;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean equals(Object v1) {
            if (!(v1 instanceof Node))
                return false;

            final Node<T> v = (Node<T>) v1;

            final boolean weightEquals = this.weight == v.weight;
            if (!weightEquals)
                return false;

            final boolean edgesSizeEquals = this.edges.size() == v.edges.size();
            if (!edgesSizeEquals)
                return false;

            final boolean valuesEquals = this.value.equals(v.value);
            if (!valuesEquals)
                return false;

            final Iterator<Edge<T>> iter1 = this.edges.iterator();
            final Iterator<Edge<T>> iter2 = v.edges.iterator();
            while (iter1.hasNext() && iter2.hasNext()) {
                // Only checking the cost
                final Edge<T> e1 = iter1.next();
                final Edge<T> e2 = iter2.next();
                if (e1.cost != e2.cost)
                    return false;
            }

            return true;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int compareTo(Node<T> v) {
            final int valueComp = this.value.compareTo(v.value);
            if (valueComp != 0)
                return valueComp;

            if (this.weight < v.weight)
                return -1;
            if (this.weight > v.weight)
                return 1;

            if (this.edges.size() < v.edges.size())
                return -1;
            if (this.edges.size() > v.edges.size())
                return 1;

            final Iterator<Edge<T>> iter1 = this.edges.iterator();
            final Iterator<Edge<T>> iter2 = v.edges.iterator();
            while (iter1.hasNext() && iter2.hasNext()) {
                // Only checking the cost
                final Edge<T> e1 = iter1.next();
                final Edge<T> e2 = iter2.next();
                if (e1.cost < e2.cost)
                    return -1;
                if (e1.cost > e2.cost)
                    return 1;
            }

            return 0;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String toString() {
            final StringBuilder builder = new StringBuilder();
            builder.append("Value=").append(value).append(" weight=").append(weight).append("\n");
            for (Edge<T> e : edges)
                builder.append("\t").append(e.toString());
            return builder.toString();
        }
    }

    public static class Edge<T extends Comparable<T>> implements Comparable<Edge<T>> {

        private Node<T> from = null;
        private Node<T> to = null;
        private int cost = 0;

        public Edge(int cost, Node<T> from, Node<T> to) {
            if (from == null || to == null)
                throw (new NullPointerException("Both 'to' and 'from' vertices need to be non-NULL."));

            this.cost = cost;
            this.from = from;
            this.to = to;
        }

        public Edge(Edge<T> e) {
            this(e.cost, e.from, e.to);
        }

        public int getCost() {
            return cost;
        }

        public void setCost(int cost) {
            this.cost = cost;
        }

        public Node<T> getFromNode() {
            return from;
        }

        public Node<T> getToNode() {
            return to;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int hashCode() {
            final int cost = (this.cost * (this.getFromNode().hashCode() * this.getToNode().hashCode())); 
            return 31 * cost;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean equals(Object e1) {
            if (!(e1 instanceof Edge))
                return false;

            final Edge<T> e = (Edge<T>) e1;

            final boolean costs = this.cost == e.cost;
            if (!costs)
                return false;

            final boolean from = this.from.equals(e.from);
            if (!from)
                return false;

            final boolean to = this.to.equals(e.to);
            if (!to)
                return false;

            return true;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int compareTo(Edge<T> e) {
            if (this.cost < e.cost)
                return -1;
            if (this.cost > e.cost)
                return 1;

            final int from = this.from.compareTo(e.from);
            if (from != 0)
                return from;

            final int to = this.to.compareTo(e.to);
            if (to != 0)
                return to;

            return 0;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
//            .append("(").append(from.weight).append(") ")
//            .append("(").append(to.weight).append(") ")
            builder.append("[ ").append(from.value).append(" ]").append(" -> ")
                   .append("[ ").append(to.value).append(" ]").append(" = ").append(cost).append("\n");
            return builder.toString();
        }
    }

    public static class CostNodePair<T extends Comparable<T>> implements Comparable<CostNodePair<T>> {

        private int cost = Integer.MAX_VALUE;
        private Node<T> vertex = null;

        public CostNodePair(int cost, Node<T> vertex) {
            if (vertex == null)
                throw (new NullPointerException("vertex cannot be NULL."));

            this.cost = cost;
            this.vertex = vertex;
        }

        public int getCost() {
            return cost;
        }

        public void setCost(int cost) {
            this.cost = cost;
        }

        public Node<T> getNode() {
            return vertex;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int hashCode() {
            return 31 * (this.cost * ((this.vertex!=null)?this.vertex.hashCode():1));
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean equals(Object e1) {
            if (!(e1 instanceof CostNodePair))
                return false;

            final CostNodePair<?> pair = (CostNodePair<?>)e1;
            if (this.cost != pair.cost)
                return false;

            if (!this.vertex.equals(pair.vertex))
                return false;

            return true;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int compareTo(CostNodePair<T> p) {
            if (p == null)
                throw new NullPointerException("CostNodePair 'p' must be non-NULL.");

            if (this.cost < p.cost)
                return -1;
            if (this.cost > p.cost)
                return 1;
            return 0;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String toString() {
            final StringBuilder builder = new StringBuilder();
            builder.append(vertex.getValue()).append(" (").append(vertex.weight).append(") ").append(" cost=").append(cost).append("\n");
            return builder.toString();
        }
    }

    public static class CostPathPair<T extends Comparable<T>> {

        private int cost = 0;
        private List<Edge<T>> path = null;

        public CostPathPair(int cost, List<Edge<T>> path) {
            if (path == null)
                throw (new NullPointerException("path cannot be NULL."));

            this.cost = cost;
            this.path = path;
        }

        public int getCost() {
            return cost;
        }

        public void setCost(int cost) {
            this.cost = cost;
        }

        public List<Edge<T>> getPath() {
            return path;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int hashCode() {
            int hash = this.cost;
            for (Edge<T> e : path)
                hash *= e.cost;
            return 31 * hash;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof CostPathPair))
                return false;

            final CostPathPair<?> pair = (CostPathPair<?>) obj;
            if (this.cost != pair.cost)
                return false;

            final Iterator<?> iter1 = this.getPath().iterator();
            final Iterator<?> iter2 = pair.getPath().iterator();
            while (iter1.hasNext() && iter2.hasNext()) {
                Edge<T> e1 = (Edge<T>) iter1.next();
                Edge<T> e2 = (Edge<T>) iter2.next();
                if (!e1.equals(e2))
                    return false;
            }

            return true;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String toString() {
            final StringBuilder builder = new StringBuilder();
            builder.append("Cost = ").append(cost).append("\n");
            for (Edge<T> e : path)
                builder.append("\t").append(e);
            return builder.toString();
        }
    }
}