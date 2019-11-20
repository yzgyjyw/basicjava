package com.jyw.lq.runtime;

public class ShutdownHook {

    public static void main(String[] args) {

        System.out.println(Thread.currentThread().getName());

        new Thread(() -> {
            try {
                Thread.sleep(10000);
                System.out.println(Thread.currentThread().getName());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("shutdown...\t" + Thread.currentThread().getName());
        }));

    }

}
