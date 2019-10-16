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
                Edge<T> reciprical = new Edge<T>(e.weight, to, from);
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
                // Only checking the weight
                final Edge<T> e1 = iter1.next();
                final Edge<T> e2 = iter2.next();
                if (e1.weight != e2.weight)
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
                // Only checking the weight
                final Edge<T> e1 = iter1.next();
                final Edge<T> e2 = iter2.next();
                if (e1.weight < e2.weight)
                    return -1;
                if (e1.weight > e2.weight)
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
        private double weight = 0;

        public Edge(double weight, Node<T> from, Node<T> to) {
            if (from == null || to == null)
                throw (new NullPointerException("Both 'to' and 'from' vertices need to be non-NULL."));

            this.weight = weight;
            this.from = from;
            this.to = to;
        }

        public Edge(Edge<T> e) {
            this(e.weight, e.from, e.to);
        }

        public double getCost() {
            return weight;
        }

        public void setCost(double weight) {
            this.weight = weight;
        }

        public Node<T> getFromNode() {
            return from;
        }

        public Node<T> getToNode() {
            return to;
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
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Edge other = (Edge) obj;
			if (from == null) {
				if (other.from != null)
					return false;
			} else if (!from.equals(other.from))
				return false;
			if (to == null) {
				if (other.to != null)
					return false;
			} else if (!to.equals(other.to))
				return false;
			if (Double.doubleToLongBits(weight) != Double.doubleToLongBits(other.weight))
				return false;
			return true;
		}

		/**
         * {@inheritDoc}
         */
        @Override
        public int compareTo(Edge<T> e) {
            if (this.weight < e.weight)
                return -1;
            if (this.weight > e.weight)
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
                   .append("[ ").append(to.value).append(" ]").append(" = ").append(weight).append("\n");
            return builder.toString();
        }
    }

    public static class CostNodePair<T extends Comparable<T>> implements Comparable<CostNodePair<T>> {

        private double weight = Double.MAX_VALUE;
        private Node<T> vertex = null;

        public CostNodePair(double weight, Node<T> vertex) {
            if (vertex == null)
                throw (new NullPointerException("vertex cannot be NULL."));

            this.weight = weight;
            this.vertex = vertex;
        }

        public double getCost() {
            return weight;
        }

        public void setCost(double weight) {
            this.weight = weight;
        }

        public Node<T> getNode() {
            return vertex;
        }

        @Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((vertex == null) ? 0 : vertex.hashCode());
			long temp;
			temp = Double.doubleToLongBits(weight);
			result = prime * result + (int) (temp ^ (temp >>> 32));
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			CostNodePair other = (CostNodePair) obj;
			if (vertex == null) {
				if (other.vertex != null)
					return false;
			} else if (!vertex.equals(other.vertex))
				return false;
			if (Double.doubleToLongBits(weight) != Double.doubleToLongBits(other.weight))
				return false;
			return true;
		}

		/**
         * {@inheritDoc}
         */
        @Override
        public int compareTo(CostNodePair<T> p) {
            if (p == null) {
            	throw new NullPointerException("CostNodePair 'p' must be non-NULL.");
            }

            if (this.weight < p.weight) {
            	return -1;
            }
            
            if (this.weight > p.weight) {
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
            builder.append(vertex.getValue()).append(" (").append(vertex.weight).append(") ").append(" weight=").append(weight).append("\n");
            return builder.toString();
        }
    }

    public static class CostPathPair<T extends Comparable<T>> {

        private double weight = 0;
        private List<Edge<T>> path = null;

        public CostPathPair(double weight, List<Edge<T>> path) {
            if (path == null) {
            	throw (new NullPointerException("path cannot be NULL."));
            }

            this.weight = weight;
            this.path = path;
        }

        public double getCost() {
            return weight;
        }

        public void setCost(double weight) {
            this.weight = weight;
        }

        public List<Edge<T>> getPath() {
            return path;
        }

        @Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((path == null) ? 0 : path.hashCode());
			long temp;
			temp = Double.doubleToLongBits(weight);
			result = prime * result + (int) (temp ^ (temp >>> 32));
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			CostPathPair other = (CostPathPair) obj;
			if (path == null) {
				if (other.path != null)
					return false;
			} else if (!path.equals(other.path))
				return false;
			if (Double.doubleToLongBits(weight) != Double.doubleToLongBits(other.weight))
				return false;
			return true;
		}

		/**
         * {@inheritDoc}
         */
        @Override
        public String toString() {
            final StringBuilder builder = new StringBuilder();
            builder.append("Cost = ").append(weight).append("\n");
            for (Edge<T> e : path)
                builder.append("\t").append(e);
            return builder.toString();
        }
    }
}