package xyz.guqing.datastructure.graph.structure;

/**
 * 有向图的节点类<br>
 *
 * @author guqing
 * @since 2019-10-18 12:35
 */
public class Node<T extends Comparable<T>> implements Comparable<Node<T>>{
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

        return weight == other.weight;
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

        return Double.compare(this.weight, o.weight);
    }
}