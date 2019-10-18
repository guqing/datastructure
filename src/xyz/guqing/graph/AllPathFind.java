package xyz.guqing.graph;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import xyz.guqing.graph.structure.Digraph;
import xyz.guqing.graph.structure.Node;

/**
 * 给定起点和终点搜索所有路径:
 * 以下方法使用如下关系构建的图描述过程:
 * v0: v0->v4
 * v1: v1->v0, v1->v2
 * v2: v2->v0, v2->v3
 * v3: v3->v4
 * v4: NULL
 * 共4个顶点,6条边
 * @author guqing
 * @param <T> 泛型<T>表示有向图Digraph存储的数据类型
 */
public class AllPathFind<T extends Comparable<T>> {
	/**
	 * all path finding graph
	 */
	private Digraph<T> graph;
	
	/**
	 * visited.contains(v) = true if v is reachable from s
	 */
	private List<Node<T>> visited;
	
	/**
	 * use v to traverse all adjacencies of the starting point
	 * and save v on the stack,when v has no adjacency point,
	 * pop a point off the stack(back up one step),
	 * if a path is found and stored in the path collection
	 * until all paths are traversed
	 */
	private Stack<Node<T>> stack;
	
	/**
	 * Save all starting and ending paths(It consists of a sequence of nodes)
	 */
	private List<List<Node<T>>> pathList = new ArrayList<>();
	
	public AllPathFind(Digraph<T> graph) {
		visited = new ArrayList<>();
		stack = new Stack<>();
		this.graph = graph;
	}
	
	public List<List<Node<T>>> findAllPath(Node<T> start, Node<T> end) {
		// 搜索路径
		dfs(start, end);
		return this.pathList;
	}
	
	/**
	 * 搜索起点到终点的所有路径:
	 * 1.从v1出发,将v1标记,并将其入栈。
	 * 2.找到v0,将其标记,将其入栈。
	 * 3.找到v4,将其标记,入栈。
	 *   v4是终点,将栈中的元素从栈底往栈顶输出,
	 *   即为一条路径。v4出栈，并取消标记，回溯到v0。
	 * 4.v0除v4外无其它出度,将v0出栈,并取消标记,回溯到v1
	 * 5.找到v2,将其标记并入栈
	 * 6.找到v0,将其标记并入栈
	 * 7.找到v4,将其标记,入栈。v4是终点,将栈中的元素
	 *   从栈底往栈顶输出,即为一条路径。v4出栈，并取消标记，回溯到v0。
	 * 8.v0除v4外无其它出度，将v0出栈，并取消标记，回溯到v2。
	 * 9.找到v3,将其标记并入栈。
	 * 10.找到v4,将其标记,入栈。v4是终点,将栈中的元素从
	 *    栈底往栈顶输出,即为一条路径。v4出栈,并取消标记,回溯到v3。
	 * 11.v3除v4外无其它出度,将v3出栈,并取消标记,回溯到v2。
	 * 12.v2除v0，v3外无其它出度，将v2出栈，并取消标记，回溯到v1。
	 * 13.v1除v0，v2外无其它出度，将v1出栈，栈空，结束遍历。
	 * @param start 起点
	 * @param end 终点
	 */
	private void dfs(Node<T> start, Node<T> end) {
		//深搜入栈查询所有路径
		//visited数组存储各定点的遍历情况，true为已遍历（标记）
		visited.add(start);
		//入栈
		stack.push(start);
		
		List<Node<T>> nodes = graph.getAdjNodes(start);
		
		//判断是否有其他出度
		if(nodes.isEmpty()) {
			//如果该顶点无其它出度,且没有找到终点,回退到上一个节点
			if(!start.equals(end)) {
				stack.pop();
				visited.remove(start);
				return;
			}
			// 如果该顶点无其它出度,且该顶点是终点,则找到路径加入容器,直接返回避免for循环判断
			List<Node<T>> path = new ArrayList<>(stack);
			// 将终点加入到路径中在存储到pathList容器
			pathList.add(path);
			return;
		}
		
		for (int i=0; i < nodes.size(); i++) {
			Node<T> v = nodes.get(i);
			//找到终点
			if (start.equals(end)) {
				//输出从栈底到栈顶的元素，即为一条路径
				List<Node<T>> path = new ArrayList<>(stack);
				// 将终点加入到路径中在存储到pathList容器
				pathList.add(path);
				
				stack.pop();//出栈
				visited.remove(start);
				break;
			}

			//该顶点未被访问过
			if (!visited.contains(v)) {
				dfs(v, end);
			}

			//如果该顶点无其它出度
			if (i == nodes.size()-1) {
				stack.pop();
				visited.remove(start);
			}
		}
	}
}
