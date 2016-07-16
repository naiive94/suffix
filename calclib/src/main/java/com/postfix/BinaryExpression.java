package com.postfix;

/**
 * Created by wangzhe on 16/7/16.
 */
public class BinaryExpression extends Expression {


    @Override
    public int infix2Result(String infix) {
        return 0;
    }

    @Override
    protected void convertPostfix(String infix) {
        if (!validInfix(infix)) {
            throw new IllegalArgumentException("input invalid");
        }
        infix = preprocess(infix);

        //结合栈把数据放进二叉树，然后遍历二叉树得到后缀表达式


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

        return infix ;
    }
}
