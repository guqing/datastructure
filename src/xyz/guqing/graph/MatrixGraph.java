package xyz.guqing.graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * 图的邻接矩阵表示
 * @author guqing
 */
public class MatrixGraph {
	private List<String> nodes;
	private int[][] edges;
	// 边的数目
	private int edgeCount;
	
	// 遍历时使用的标记
	private int initialCapacity;
	private boolean[] marked;
	
	
	public MatrixGraph() {
		this(1);
	}
	
	public MatrixGraph(int initialCapacity) {
		if(initialCapacity < 1) {
			initialCapacity = 1;
		}
		// 初始化矩阵和顶点
		edges = new int[initialCapacity][initialCapacity];
		nodes = new ArrayList<>(initialCapacity);
		edgeCount = 0;
		// 为全局初始化容量赋值
		this.initialCapacity = initialCapacity;
	}
	
	public int getNodeCount() {
		return nodes.size();
	}
	
	public int getEdgeCount() {
		return edgeCount;
	}
	
	public String get(int index) {
		return nodes.get(index);
	}
	
	public int getWeight(int v1, int v2) {
		return edges[v1][v2];
	}
	
	public void show() {
		for(int[] edge : edges) {
			System.out.println(Arrays.toString(edge));
		}
	}
	
	// 添加顶点
	public void addNode(String node) {
		nodes.add(node);
	}
	
	/**
	 * @param v1 表示第1个点的下标例如表示A-B的关系 A->0 B->1
	 * @param v2 表示第2个点的下标
	 * @param weight 表示关系值,用1表示有关系
	 */
	public void addEdge(int v1, int v2, int weight) {
		edges[v1][v2] = weight;
		edges[v2][v1] = weight;
		edgeCount++;
	}
	
	// 重载dfs,遍历所有的节点
	public String dfs() {
		// 为了深度优先遍历和广度优先遍历都可以使用，这个公用的marked需要单独初始化
		marked = new boolean[initialCapacity];
		List<String> list = new ArrayList<>();
		for(int i=0; i<getNodeCount(); i++) {
			if(!marked[i]) {
				dfs(marked, i, list);
			}
		}
		return list.toString();
	}
	
	// 得到第一个邻接点的下标w
	private int getFirstAdjNode(int index) {
		for(int i=0; i< nodes.size(); i++) {
			if(edges[index][i] > 0) {
				return i;
			}
		}
		return -1;
	}
	
	// 根据前一个邻接点的下标来获取下一个邻接节点
	private int getNextAdjNode(int v1, int v2) {
		for(int j = v2 + 1; j<nodes.size(); j++) {
			if(edges[v1][j] > 0) {
				return j;
			}
		}
		return -1;
	}
	
	// 深度优先遍历
	private void dfs(boolean[] marked, int index, List<String> list) {
		// 首先访问该节点即添加到容器中最后统一输出
		list.add(get(index));
		
		// 将该节点设置为已访问
		marked[index] = true;
		
		// 查找节点index的第一个邻接节点w
		int w = getFirstAdjNode(index);
		// 有邻接节点
		while(w != -1) {
			if(!marked[w]) {
				// 该节点没有被访问过
				dfs(marked, w, list);
			}
			// 如果w节点已经被访问过,查找邻接节点的下一个节点
			w = getNextAdjNode(index, w);
		}
	}
	
	// 遍历所有的节点,都进行广度优先搜索
	public String bfs() {
		// 为了深度优先遍历和广度优先遍历都可以使用，这个公用的marked需要单独初始化
		marked = new boolean[initialCapacity];
		
		List<String> list = new ArrayList<>();
		for(int i = 0; i<getNodeCount(); i++) {
			if(!marked[i]) {
				bfs(marked, i, list);
			}
		}
		
		return list.toString();
	}
	
	/**
	 * 广度优先遍历
	 */
	private void bfs(boolean[] marked, int index, List<String> list) {
		// 队列的头节点对应的下标
		int u;
		// 邻接节点w
		int w;
		
		// 队列,记录节点的访问顺序
		LinkedList<Integer> queue = new LinkedList<>();
		
		// 输出该元素
		list.add(get(index));
		
		// 标记为已访问
		marked[index] = true;
		// 将节点加入队列
		queue.addLast(index);
		
		while(!queue.isEmpty()) {
			// 队列非空,从队头取出数据,并从队列删除它
			u = queue.removeFirst();
			// 得到第一个邻接点的下标w
			w = getFirstAdjNode(u);
			while(w != -1) {
				// 如果w存在,判断是否访问过
				if(!marked[w]) {
					// 输出该元素
					list.add(get(w));
					
					// 标记为已访问
					marked[w] = true;
					// 加入队列
					queue.addLast(w);
				}
				// 如果已经访问过,以u为前驱节点寻找w后的下一个邻接点
				w = getNextAdjNode(u, w);
			}
		}
	}
	
	public static void main(String[] args) {
		String[] nodes = {"A", "B", "C", "D", "E"};
		MatrixGraph graph = new MatrixGraph(5);
		// 添加顶点
		for(String node : nodes) {
			graph.addNode(node);
		}
		// 添加边,描述边的关系
		// A-B,A-C,B-C,B-D,B-E
		graph.addEdge(0, 1, 1);//A-B
		graph.addEdge(0, 2, 1);//A-C
		graph.addEdge(1, 2, 1);//B-C
		graph.addEdge(1, 3, 1);//B-D
		graph.addEdge(1, 4, 1);//B-E
		
		graph.show();
		
		// 测试深度优先遍历
		System.out.println("深度优先遍历:\n" + graph.dfs());
		
		// 测试广度优先遍历
		System.out.println("广度优先遍历：\n" + graph.bfs());
	}
}
