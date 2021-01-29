package com.jyw.lq.staticinit;

public class D {

    public static void main(String[] args) {
        new Thread(()->{

            B.bMethod();

        }).start();

        new Thread(()->{

            A.aMethod();

        }).start();
    }

}
