package com.jyw.lq.jdk8;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class CompletableFutureDemo {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        allof();
    }

    private static void demo01() throws InterruptedException, ExecutionException {
                System.out.println("主线程:" + Thread.currentThread().getName());
                //supplyAsync:从全局的 ForkJoinPool.commonPool()获得一个线程中执行这些任务
                CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> {
                    System.out.println("子线程:" + Thread.currentThread().getName());
                    try {
                        Thread.sleep(100);
                        throw new RuntimeException("Completable future error");
                    } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return "Completable success";
            //handle方法,无论CompletableFuture中执行的任务是否抛出异常都会调用,不过如果没有异常exception为null
        }).handle((res, exception) -> {
            if (exception != null) {
                System.out.println("Completable error," + exception);
                return "error";
            } else {
                return res;
            }
        });
        //模拟其它耗时操作
        Thread.sleep(1000);
        System.out.println("completableFuture result: " + completableFuture.get());

        //除了supplyAsync方法,异步执行的任务有返回值,还有runAsync用于异步执行那些不需要返回值的任务
}

    private static void thenApply() throws ExecutionException, InterruptedException {
        System.out.println("主线程:" + Thread.currentThread().getName());
        CompletableFuture<Integer> completableFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println("completableFuture:" + Thread.currentThread().getName());
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "hello";
        }).thenApply(str -> {
            //使用thenApply 不会新建一个线程执行,而是在与supplyAsync中的同一个线程中执行,如果需要在另一个线程中执行,使用thenApplySync
            System.out.println("1st thenApply:" + Thread.currentThread().getName());
            return "hello," + str;
        }).thenApply(str -> {
            System.out.println("2end thenApply:" + Thread.currentThread().getName());
            return str.length();
        });

        //除了thenApply，当不需要返回值时还可以使用thenAccept和thenRun
        //thenAccept可以获取supplyAsync的结果
        //thenRun不可以获取supplyAsync的结果

        System.out.println(completableFuture.get());
    }

    private static void allof() throws InterruptedException {
        CompletableFuture completableFuture1 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "abc1";
        });

        CompletableFuture completableFuture2 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "abc2";
        });

        CompletableFuture completableFuture3 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "abc3";
        });

        CompletableFuture<Void> allOfCompletableFuture = CompletableFuture.allOf(completableFuture1, completableFuture2, completableFuture3);

        //allof,只有所有的completableFuture都完成的时候才会执行thenAccept,thenApply等
        allOfCompletableFuture.thenAccept((v) -> {
            try {
                System.out.println(System.currentTimeMillis() + " " + completableFuture1.get());
                System.out.println(System.currentTimeMillis() + " " + completableFuture2.get());
                System.out.println(System.currentTimeMillis() + " " + completableFuture3.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        });


        //与allof相对应的还有一个anyOf()
        Thread.sleep(2000);
    }


}
