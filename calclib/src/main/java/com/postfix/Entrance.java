package com.postfix;

/**
 * Created by wangzhe on 16/7/15.
 */
public class Entrance {

    public static void main(String[] args) {
        String infix = "-(-1+23)*8-(9+3)/4+5/20";

        try {
            Expression expression = ExpressionFactory.createShuntingExp();
            double result = expression.infix2Result(infix);
            System.out.print(result + "");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
