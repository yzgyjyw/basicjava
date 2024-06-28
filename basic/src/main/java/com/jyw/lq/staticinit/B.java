package com.jyw.lq.staticinit;

public class B {

    private static C c = C.getC();


    public static String getBConstans() {
        return A.A + "B";
//        return "";
    }

    public static void bMethod() {
        System.out.println("bMethod");
    }

}





