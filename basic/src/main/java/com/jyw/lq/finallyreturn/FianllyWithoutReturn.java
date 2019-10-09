package com.jyw.lq.finallyreturn;

/**
 * fianlly块是在return语句执行之后,return返回之前执行
 */
public class FianllyWithoutReturn {
    public static void main(String[] args) {
//        System.out.println(method01());

        System.out.println(method02());
    }

    public static int method01() {
        int b = 20;
        try {
            System.out.println("try block");
            return b += 80;
        } catch (Exception e) {
            System.out.println("catch block");
        } finally {
            System.out.println("finally block");

            if (b > 25) {
                System.out.println("b>25, b = " + b);
            }
        }
        return b;
    }

    public static String method02() {
        try {
            System.out.println("try block");
            return test12();
        } finally {
            System.out.println("finally block");
        }
    }

    public static String test12() {
        System.out.println("return statement");

        return "after return";
    }
}
