package xyz.guqing.datastructure.stack;

import java.util.ArrayList;
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
		//String suffixExpression1 = "3 4 + 5 * 6 -";
		String suffixExpression2 = "30 4 + 5 * 6 -";
		
		// 1.先将suffixExpression装入一个ArrayList中
		List<String> rpList = getList(suffixExpression2);
		// 2.使用ArrayList传递给一个方法，遍历配合栈完成计算
		double result = calculate(rpList);
		System.out.println("计算结果：" + result);
		
		/**
		 * 将中缀表达式转后缀表达式结果测试
		 */
		//String expression = "11 + ((2+3)*4)-5";
		String expression = "(3.2 + 4)*5-6";
		List<String> suffix = infixToSuffix(expression);
		double solution = calculate(suffix);
		System.out.println("中缀转后缀表达式计算结果:" + solution );
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
	public static double calculate(List<String> list) {
		// 创建一个栈
		Stack<String> stack = new Stack<String>();
		// 遍历list
		for(String item : list) {
			// 正则匹配数字
			if(item.matches("\\d+\\.?\\d*")) {
				// 将数字入栈
				stack.push(item);
			} else {
				// 由于当前是else说明item是一个符号
				// pop出两个数，并计算，再入栈
				double num2 = Double.parseDouble(stack.pop());
				double num1 = Double.parseDouble(stack.pop());
				
				// 计算
				double result = switchCalc(item, num1, num2);
				// 入栈
				stack.push(result + "");
			}
		}
		return Double.parseDouble(stack.pop());
	}
	
	public static double switchCalc(String item, double num1, double num2) {
		double result = 0;
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
	
	/**
	 * 中缀表达式转后缀表达式
	 */
	public static List<String> infixToSuffix(String expression) {
		// 先将中缀表达式转成中缀的List
		List<String> infixList = infixToList(expression);
		
		// 定义存储符号栈和中间结果的栈
		Stack<String> stack1 = new Stack<>();
		// stack2这个栈在整个转换过程中没有pop操作，而且最后还要逆序，所以使用list替换stack2
		List<String> stack2 = new ArrayList<>();
		
		for(String item : infixList) {
			// 如果是一个数就入stack2
			if(item.matches("\\d+\\.?\\d*")) {
				stack2.add(item);
			} else if(item.equals("(")) {
				// 是左括号如stack1
				stack1.push(item);
			} else if(item.equals(")")) {
				// 是右括号，则依次弹出stack1栈顶的运算符并压入stack2，直到遇到左括号为止，此时将这一对括号丢弃
				while(!stack1.peek().equals("(")) {
					stack2.add(stack1.pop());
				}
				// 剔除这一对括号
				stack1.pop();
			} else {
				/**
				 * 当item的优先级小于等于栈顶运算符的优先级
				 * 将stack1栈顶的运算符弹出并加入到stack2中
				 * 在次转到(4,1)与stack1中新的栈顶运算符相比较
				 */
				while(stack1.size() != 0 && Operation.getValue(stack1.peek()) >= Operation.getValue(item)) {
					stack2.add(stack1.pop());
				}
				// 把item运算符压入栈中
				stack1.push(item);
			}
		}
		// 将stack1中剩余的运算发依次弹出加入到stack2中
		while(stack1.size() != 0) {
			stack2.add(stack1.pop());
		}
		
		return stack2;
	}
	/**
	 * @param expression 中缀表达式
	 * @return 返回中缀表达式字符List
	 */
	public static List<String> infixToList(String expression){
		// 去除所有空格
		expression = expression.replace(" ", "");

		// 提取出所有的数学符号，即剔除数字\\d*
		String operator = expression.replaceAll("\\d+\\.?\\d*", "");  

        List<String> list=new ArrayList<String>();  
        int pidx = -1;  
        for(int i=0; i<operator.length(); i++){  
            String p = operator.substring(i, i+1);  
            pidx = expression.indexOf(p);  
            if(expression.substring(0,pidx).trim().length() != 0){  
                list.add(expression.substring(0, pidx));  
            }  
            list.add(expression.substring(pidx, pidx+1));  
            expression = expression.substring(pidx+1);  
        }  
        if(expression.length()>0){  
            list.add(expression);  
        }  
		return list;
	}
	
	private static class Operation {
		private static final int ADD = 1; 
		private static final int SUB = 1;
		private static final int MUL = 2;
		private static final int DIVSION = 2;
		
		// 返回对应的优先级数字
		public static int getValue(String operation) {
			int result = 0;
			switch (operation) {
			case "+":
				result = ADD;
				break;
			case "-":
				result = SUB;
				break;
			case "*":
				result = MUL;
				break;
			case "/":
				result = DIVSION;
				break;
			default:
				break;
			}
			return result;
		}
	}
}
