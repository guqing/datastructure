package xyz.guqing.datastructure.recursion;

import java.util.Arrays;
import java.util.Random;

/**
 * 递归迷宫问题
 * @author guqing
 */
public class Maze {
	public static void main(String[] args) {
		int[][] map = getMap();
		// map是引用类型，寻完路后在输出map即为走过的路
		pathFind(map, 1, 1);
		System.out.println("寻路后的地图：");
		for(int i=0; i<map.length; i++) {
			System.out.println(Arrays.toString(map[i]));
		}
	}
	
	public static int[][] getMap() {
		// 创建二维数组模拟迷宫
		int[][] map = new int[8][7];
		// 使用1表示墙壁
		Random random = new Random();
		int row = map.length;
		int col = map[0].length;
		for(int i=0; i<row; i++) {
			for(int j=0; j<col; j++) {
				int value = random.nextInt(2);
				map[i][j] = value;
			}
		}
		// 将二维数组的四周都设为1，即代表墙，避免寻路时超出数组边界，这一点很关键
		for(int i=0; i<row; i++) {
			map[i][0] = 1;
			map[i][col-1] = 1;
		}
		for(int j=0; j<col; j++) {
			map[0][j] = 1;
			map[row-1][j] = 1;
		}
		
		// 将起点和终点都设置为0，避免随机时生成的是1，墙是不能走的
		map[1][1] = 0;
		map[6][5] = 0;
		return map;
	}
	
	/**
	 * 使用递归回溯来给寻路
	 * i,j表示从地图的那个位置开始出发比如(1,1)
	 * 如果小球能到map[6][5]位置，则说明通路找到
	 * 当map[i][j]为0表示该点还没有走过，如果为2表示为通路可以走
	 * 为3表示该位置已经走过但是不通
	 * 策略：下->右->上->左，如果该点走不通在回溯
	 * @param map 地图
	 * @param i 从哪个位置开始找
	 * @param j
	 * @return 如果找到通路返回true，否则返回false
	 */
	public static boolean pathFind(int[][] map, int i, int j) {
		if(map[6][5] == 2) {
			// 已经找到
			return true;
		} else {
			// 如果当前点还没有走过
			if(map[i][j] == 0) {
				// 按照策略走:下->右->上->左
				//假设该点可以走通设置为2，走不通在置为3
				map[i][j] = 2;
				if(pathFind(map, i+1, j)) {
					// 向下走i+1
					return true;
				} else if(pathFind(map, i, j+1)){
					// 向右走j+1
					return true;
				} else if(pathFind(map, i-1, j)) {
					// 向上走i-1
					return true;
				} else if(pathFind(map, i, j-1)){
					// 向做走j-1
					return true;
				} else {
					// 走不通,置为3
					map[i][j] = 3;
					return false;
				}
			} else {
				// 如果map[i][j] != 0，可能等于1墙，可能是2走过了，可能是3死路
				return false;
			}
		}
	}
}
