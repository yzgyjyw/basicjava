package com.jyw.lq.staticinit;

public class A {

    public static String A = "A";

//    public static String A2 = null;

    public static  C c = C.getC();


    static {
//        A2 = A + B.getBConstans();
    }


    public static void aMethod(){
        System.out.println("aMethod");
    }
}
