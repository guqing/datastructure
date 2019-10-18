package xyz.guqing.graph.structure;

import com.sun.istack.internal.NotNull;

/**
 * 有向图的带权边<br>
 *
 * @author guqing
 * @date 2019-10-18 12:36
 */
public class Edge<T extends Comparable<T>> implements Comparable<Edge<T>>{
    /**
     * 边的起始顶点
     */
    private Node<T> from;

    /**
     * 边的终止顶点
     */
    private Node<T> to;

    /**
     *  边的权重
     */
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

        return Double.doubleToLongBits(weight) == Double.doubleToLongBits(other.weight);
    }

    /**
     * 先按照from顶点排序,如果相等按照to顶点排序,否则按照权重weight排序
     */
    @Override
    public int compareTo(@NotNull Edge<T> e) {
        final int fromCmp = this.from.compareTo(e.from);
        if(fromCmp != 0) {
            return fromCmp;
        }

        final int toCmp = this.to.compareTo(e.to);
        if (toCmp != 0) {
            return toCmp;
        }

        return Double.compare(this.weight, e.weight);
    }
}