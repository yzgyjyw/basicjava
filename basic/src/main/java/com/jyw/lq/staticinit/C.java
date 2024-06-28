package com.jyw.lq.staticinit;

public class C {

    private static volatile C c;

    public static C getC() {
        if (c == null) {
            synchronized (C.class) {
                if (c == null) {
                    String cConstant = B.getBConstans();
                }
            }
        }
        return c;
    }
}
