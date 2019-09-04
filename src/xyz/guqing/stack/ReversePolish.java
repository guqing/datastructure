package xyz.guqing.stack;

import java.util.Arrays;
import java.util.List;
import java.util.Stack;

/**
 * 使用栈完成逆波兰计算器
 * @author guqing
 * @date 2019/9/4
 */
public class ReversePolish {
	public static void main(String[] args) {
		/**
		 * 先定义一个逆波兰表达式
		 * (3+4)*5-6 => 3 4 + 5 * 6 - => 29
		 * (30+4)*5-6 => 30 4 + 5 * 6 => 164
		 * 使用空格隔开方便处理
		 */
		String suffixExpression1 = "3 4 + 5 * 6 -";
		String suffixExpression2 = "30 4 + 5 * 6 -";
		
		// 1.先将suffixExpression装入一个ArrayList中
		List<String> rpList = getList(suffixExpression2);
		// 2.使用ArrayList传递给一个方法，遍历配合栈完成计算
		int result = calculate(rpList);
		System.out.println("计算结果：" + result);
	}
	
	/**
	 * 将逆波兰表达式，依次将数据和运算符放入ArrayList
	 */
	public static List<String> getList(String suffixExpression) {
		// 将suffixExpression分割
		String[] splitArray = suffixExpression.split(" ");
		// 转成list，注意这种方式转成的list和真正的List的区别
		return Arrays.asList(splitArray);
	}
	/**
	 * 1.从左至右扫描，将3和4压入堆栈
	 * 2.遇到+运算符，弹出4和3(4是栈顶元素，3是次栈顶元素)，计算出3+4的值得到7，再将7入栈
	 * 3.将5入栈
	 * 4.接下来是*运算符，因此弹出5和7，计算出7*5=35，将35入栈
	 * 5.将6入栈
	 * 6.最后是-运算符，计算出35-6的值即29，由此得出最终结果
	 * @param list 存储着逆波兰表达式每个元素的字符串集合
	 * @return 返回逆波兰计算器的计算结果
	 */
	public static int calculate(List<String> list) {
		// 创建一个栈
		Stack<String> stack = new Stack<String>();
		// 遍历list
		for(String item : list) {
			// 正则匹配数字
			if(item.matches("\\d+")) {
				// 将数字入栈
				stack.push(item);
			} else {
				// 由于当前是else说明item是一个符号
				// pop出两个数，并计算，再入栈
				int num2 = Integer.parseInt(stack.pop());
				int num1 = Integer.parseInt(stack.pop());
				
				// 计算
				int result = switchCalc(item, num1, num2);
				// 入栈
				stack.push(result + "");
			}
		}
		return Integer.parseInt(stack.pop());
	}
	
	public static int switchCalc(String item, int num1, int num2) {
		int result = 0;
		switch (item) {
		case "+":
			result = num1 + num2;
			break;
		case "-":
			result = num1 - num2;
			break;
		case "*":
			result = num1 * num2;
			break;
		case "/":
			result = num1 / num2;
			break;
		case "%":
			result = num1 % num2;
			break;
		default:
			break;
		}
		return result;
	}
}
