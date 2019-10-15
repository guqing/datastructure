package xyz.guqing.graph.linked;

public class Topo<T> {
	private T source;
	private T target;
	private int cost;
	
	
	public Topo() {
	}

	public Topo(T source, T target, int cost) {
		this.source = source;
		this.target = target;
		this.cost = cost;
	}

	public T getSource() {
		return source;
	}


	public void setSource(T source) {
		this.source = source;
	}


	public T getTarget() {
		return target;
	}


	public void setTarget(T target) {
		this.target = target;
	}


	public int getCost() {
		return cost;
	}


	public void setCost(int cost) {
		this.cost = cost;
	}


	@Override
	public String toString() {
		return "Topo [source=" + source + ", target=" + target + ", cost=" + cost + "]";
	}
	
	
}
