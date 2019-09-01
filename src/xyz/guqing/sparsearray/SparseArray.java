package xyz.guqing.sparsearray;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class SparseArray {
	public static void main(String[] args) throws IOException {
		/**
		 * 创建一个原始的二维数组 11*11
		 * 0：表示没有棋子，1表示黑子，2表示白子
		 */
		int[][] chessArray = new int[11][11];
		chessArray[1][2] = 1;
		chessArray[2][3] = 2;
		// 输出原始的二维数组
		System.out.println("原始的二维数组:\n");
		toStringArray(chessArray);
		
		/**
		 *  将二维数组转为稀疏数组
		 *  1.先遍历二维数组得到非0数据的个数
		 *  2.创建对应的稀疏数组
		 */
		int effectiveDataCount = 0;
		for(int i = 0; i < 11; i++) {
			for(int j = 0; j<11; j++) {
				if(chessArray[i][j] != 0) {
					effectiveDataCount++;
				}
			}
		}
		/**
		 * 创建对应的稀疏数组,行是有效数据的行数+1
		 * 多出的一行用来存储原二维数组的行和列以及有效数据的个数
		 * 3列是固定值，第一列存储行，第二列存储列，第三列存储实际的值
		 */
		int[][] sparseArray = new int[effectiveDataCount + 1][3];
		// 给稀疏数组赋值，初始化第一行的值
		sparseArray[0][0] = 11;
		sparseArray[0][1] = 11;
		sparseArray[0][2] = effectiveDataCount;
		
		/**
		 * 遍历二维数组，将非0的值存放到稀疏数组中
		 * 再次遍历，如果不进行第一次遍历无法创建稀
		 * 疏数组的大小除非使用集合
		 */
		// 用于记录是第几个非0数据
		int count = 0;
		for(int i = 0; i < 11; i++) {
			for(int j = 0; j<11; j++) {
				if(chessArray[i][j] != 0) {
					count++;
					// 因为count = 0而存值是从第一行开始所以count先++
					sparseArray[count][0] = i;
					sparseArray[count][1] = j;
					sparseArray[count][2] = chessArray[i][j];
				}
			}
		}
		// 输出稀疏数组
		System.out.println("稀疏数组为：\n");
		toStringArray(sparseArray);
		
		/**
		 * 稀疏数组转原始二维数组
		 * 1.先读取稀疏数组的第一行，根据第一行数据创建原始二维数组
		 * 2.读取稀疏数组剩下的行数据，并赋值给原始的二维数组
		 */
		int row = sparseArray[0][0];
		int col = sparseArray[0][1];
		int[][] originalArray = new int[row][col];
		// 遍历稀疏数组剩下的行数据赋值给原始数组,从第二行开始即index=1
		for(int i = 1; i < sparseArray.length; i++) {
			// 获取数据所在原始数组的行号
			int originRow = sparseArray[i][0];
			// 获取数据所在原始数组的列号
			int originCol = sparseArray[i][1];
			// 赋值
			originalArray[originRow][originCol] = sparseArray[i][2];
		}
		System.out.println("稀疏数组恢复原始数组：\n");
		toStringArray(originalArray);
		
		// 将稀疏二维数组写入到文件并读取
		readWriteSparseArray(sparseArray);
	}
		
	/**
	 * 格式化打印二维数组
	 * @param array
	 */
	private static void toStringArray(int[][] array) {
		for(int[] row : array) {
			for(int data : row) {
				System.out.printf("%d\t", data);
			}
			System.out.println("\n");
		}
	}
	
}
