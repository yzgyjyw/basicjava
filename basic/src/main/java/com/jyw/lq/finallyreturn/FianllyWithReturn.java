package com.jyw.lq.finallyreturn;

/**
 * fianlly块中有return语句时,就不会管try中有没有return了
 * 有个细节,如果finally块中加上了return,那么fianlly外面的return b就不可达了,需要注释掉才能编译
 */
public class FianllyWithReturn {
    public static void main(String[] args) {
        System.out.println(test2());
    }
    public static int test2() {
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

            return 200;
        }

        // return b;
    }

}
