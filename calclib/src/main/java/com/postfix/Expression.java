package com.postfix;

import java.util.Arrays;
import java.util.List;
import java.util.Stack;

/**
 * 中缀表达式转换为后缀表达式（逆波兰表达式），即调度场算法(shunting yard algorithm)
 * 1.建立运算符栈用于运算符的存储，此运算符遵循越往栈顶优先级越高的原则。
 * 2.预处理表达式，正、负号前加0(如果一个加号（减号）出现在最前面或左括号后面，则该加号（减号） 为正负号)。
 * 3.顺序扫描表达式，如果当前字符是数字（优先级为0的符号），则直接输出该数字；如果当前字符为运算符或者括号（优先级不为0的符号），则判断第四点。
 * 4.1 若当前运算符为'('，直接入栈；
 * 4.2 若为')'，出栈并顺序输出运算符直到遇到第一个'(',遇到的第一个'('出栈但不输出；
 * 4.3 若为其它，比较运算符栈栈顶元素与当前元素的优先级：
 * 4.3.1 如果栈顶元素是'('，当前元素直接入栈；
 * 4.3.2 如果栈顶元素优先级>=当前元素优先级，出栈并顺序输出运算符直到栈顶元素优先级<当前元素优先级，然后当前元素入栈；
 * 4.3.3 如果栈顶元素优先级<当前元素优先级，当前元素直接入栈。
 * 5.重复第三点直到表达式扫描完毕。
 * 6.顺序出栈并输出运算符直到栈元素为空。
 * <p/>
 * <p/>
 * Created by wangzhe on 16/7/16.
 */
public abstract class Expression {

    public abstract double infix2Result(String infix);

    protected abstract String convertPostfix(String infix);

    /**
     * 预处理，负数补全0或者补全括号
     *
     * @param infix
     * @return
     */
    public abstract String preprocess(String infix);


    protected double postfixArithmetic(String postfix) {
        Stack<Double> stack = new Stack<>();
        String[] array = postfix.split(" ");
        for (int i = 0; i < array.length; i++) {
            array[i].trim();
            if (Character.isDigit(array[i].charAt(0))) {
                stack.push(Double.valueOf(array[i]));
            } else {
                double y = stack.pop();
                double x = stack.pop();
                stack.push(calculate(x, y, array[i]));
            }

        }
        return stack.pop();
    }

    protected double calculate(double x, double y, String operator) {
        double ret = 0.0;
        switch (operator) {
            case "+":
                ret = x + y;
                break;
            case "-":
                ret = x - y;
                break;
            case "*":
                ret = x * y;
                break;
            case "/":
                if (y == 0) throw new IllegalArgumentException("Divisor cannot be zero");
                ret = x / y;
                break;
        }
        return ret;
    }

    //此函数用正则表达式匹配效果更好
    protected boolean validInfix(String infix) {
        Character[] validChar = {'+', '-', '*', '/', '(', ')'};
        List<Character> validChars = Arrays.asList(validChar);
        int leftBracket = 0;
        int rightBracket = 0;
        boolean invalid = false;
        char c;
        for (int i = 0; i < infix.length(); i++) {
            c = infix.charAt(i);
            if (c == '(') leftBracket++;
            if (c == ')') rightBracket++;
            if (!(validChars.contains(c) || Character.isDigit(c))) invalid = true;
        }
        return leftBracket == rightBracket && !invalid;
    }

    /**
     * 比较优先级高低
     *
     * @param a
     * @param b
     * @return
     */
    protected boolean higherPriority(char a, char b) {
        return priority(a) >= priority(b);
    }

    private int priority(char c) {
        int p = 0;
        switch (c) {
            case '(':
                p = 4;
                break;
            case '*':
            case '/':
                p = 3;
                break;
            case '+':
            case '-':
                p = 2;
            default:
                p = 0;
                break;
        }
        return p;
    }
}
