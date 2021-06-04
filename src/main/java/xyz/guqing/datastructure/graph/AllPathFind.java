package xyz.guqing.datastructure.graph;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import xyz.guqing.datastructure.graph.structure.Digraph;
import xyz.guqing.datastructure.graph.structure.Node;

/**
 * 给定起点和终点搜索所有路径:
 * <div>以下方法使用如下关系构建的图描述过程:
 * <li>v0: v0->v4
 * <li>v1: v1->v0, v1->v2
 * <li>v2: v2->v0, v2->v3
 * <li>v3: v3->v4
 * <li>v4: NULL
 * 共4个顶点,6条边
 *
 * @param <T> 泛型<T>表示有向图Digraph存储的数据类型
 * @author guqing
 * @since 2019-10-18
 */
public class AllPathFind<T extends Comparable<T>> {

    /**
     * all path finding graph
     */
    private final Digraph<T> graph;

    /**
     * Save all starting and ending paths(It consists of a sequence of nodes)
     */
    private final List<List<Node<T>>> pathList = new ArrayList<>();

    public AllPathFind(Digraph<T> graph) {
        this.graph = graph;
    }

    /**
     * 搜索起点到终点的所有路径:
     * <li>1.从v1出发,将v1标记,并将其入栈。
     * <li>2.找到v0,将其标记,将其入栈。
     * <li>3.找到v4,将其标记,入栈。 v4是终点,将栈中的元素从栈底往栈顶输出,
     * 即为一条路径。v4出栈，并取消标记，回溯到v0。
     * <li>4.v0除v4外无其它出度,将v0出栈,并取消标记,回溯到v1
     * <li>5.找到v2,将其标记并入栈
     * <li>6.找到v0,将其标记并入栈
     * <li>7.找到v4,将其标记,入栈。v4是终点,将栈中的元素 从栈底往栈顶输出,即为一条路径。v4出栈，并取消标记，回溯到v0。
     * <li>8.v0除v4外无其它出度，将v0出栈，并取消标记，回溯到v2。
     * <li>9.找到v3,将其标记并入栈。
     * <li>10.找到v4,将其标记,入栈。v4是终点,将栈中的元素从 栈底往栈顶输出,即为一条路径。v4出栈,并取消标记,回溯到v3。
     * <li>11.v3除v4外无其它出度,将v3出栈,并取消标记,回溯到v2。 12.v2除v0，v3外无其它出度，将v2出栈，并取消标记，回溯到v1。
     * <li>13.v1除v0，v2外无其它出度，将v1出栈，栈空，结束遍历。
     *
     * @param s 起点
     * @param d 终点
     */
    public List<List<Node<T>>> findAllPath(Node<T> s, Node<T> d) {
        Set<Node<T>> isVisited = new HashSet<>();

        ArrayList<Node<T>> pathList = new ArrayList<>();

        // add source to path[]
        pathList.add(s);

        // Call recursive utility
        dfs(s, d, isVisited, pathList);
        return this.pathList;
    }

    /**
     * all paths from 'u' to 'd'.
     *
     * @param u start vertex
     * @param d end vertex
     * @param isVisited keeps track of vertices in current path.
     * @param localPathList stores actual vertices in the current path
     */
    private void dfs(Node<T> u, Node<T> d,
        Set<Node<T>> isVisited,
        List<Node<T>> localPathList) {

        if (u.equals(d)) {
            System.out.println(localPathList);
            List<Node<T>> path = new ArrayList<>(localPathList);
            pathList.add(path);
            // if match found then no need to traverse more till depth
            return;
        }

        // Mark the current node
        isVisited.add(u);

        // Recur for all the vertices
        // adjacent to current vertex
        for (Node<T> i : graph.getAdjNodes(u)) {
            if (!isVisited.contains(i)) {
                // store current node
                // in path[]
                localPathList.add(i);
                dfs(i, d, isVisited, localPathList);

                // remove current node
                // in path[]
                localPathList.remove(i);
            }
        }

        // Mark the current node
        isVisited.remove(u);
    }
}