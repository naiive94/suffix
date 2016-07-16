package com.postfix;

import java.util.Stack;

/**
 * Created by wangzhe on 16/7/15.
 */
public class ShuntingExpression extends Expression {

    private Stack<Character> operatorStack = new Stack<>();
    private StringBuilder postfix = new StringBuilder();

    @Override
    public int infix2Result(String infix) {

        convertPostfix(infix);

        return postfixArithmetic(postfix.toString());
    }

    @Override
    protected void convertPostfix(String infix) {
        if (!validInfix(infix)) {
            throw new IllegalArgumentException("input invalid");
        }
        infix = preprocess(infix);

        for (int i = 0; i < infix.length(); i++) {
            char c = infix.charAt(i);
            switch (c) {
                case '(':
                    operatorStack.push(c);
                    break;
                case ')':
                    while (!operatorStack.isEmpty()) {
                        if (operatorStack.peek().equals('(')) {
                            operatorStack.pop();
                            break;
                        } else {
                            postfix.append(" ");
                            postfix.append(operatorStack.pop());
                        }
                    }
                    break;
                case '+':
                case '-':
                case '*':
                case '/':
                    if (!operatorStack.isEmpty()) {
                        if (operatorStack.peek().equals('(')) {
                            operatorStack.push(c);
                            postfix.append(" ");
                            break;
                        }
                        while (!operatorStack.isEmpty() && higherPriority(operatorStack.peek(), c)) {
                            if (operatorStack.peek().equals('(')) {
                                break;
                            }
                            postfix.append(" ");
                            postfix.append(operatorStack.pop());
                        }
                    }
                    operatorStack.push(c);
                    postfix.append(" ");
                    break;

                default:
                    postfix.append(c);
                    break;
            }
        }
        while (!operatorStack.isEmpty()) {
            postfix.append(" ");
            postfix.append(operatorStack.pop());
        }
    }


    @Override
    public String preprocess(String infix) {
        if (infix.startsWith("-")) {
            infix = "0" + infix;
        }
        for (int i = 0; i < infix.length(); i++) {
            if (infix.charAt(i) == '(' && i + 1 < infix.length() && (infix.charAt(i + 1) == '-' || infix.charAt(i + 1) == '+')) {
                infix = infix.substring(0, i + 1) + "0" + infix.substring(i + 1);
            }
        }
        System.out.println(infix);
        return infix;
    }


}
