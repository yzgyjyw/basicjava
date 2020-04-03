package com.jyw.accumulate;

import java.util.concurrent.*;

public class QpsAccumulateTest {

    public static void main(String[] args) throws InterruptedException {



        ExecutorService executorService = new ThreadPoolExecutor(10,20,0L,TimeUnit.MILLISECONDS,new LinkedBlockingQueue<>());
        QpsAccumulate qpsAccumulate = new QpsAccumulate();
        for (int i = 0; i < 10000000; i++) {
            executorService.execute(()->qpsAccumulate.incr(100));
        }

        for (int i = 0; i < 10000000; i++) {
            executorService.execute(()->qpsAccumulate.incr(200));
        }

        for (int i = 0; i < 10000000; i++) {
            executorService.execute(()->qpsAccumulate.incr(300));
        }

        for (int i = 0; i < 10000000; i++) {
            executorService.execute(()->qpsAccumulate.incr(100));
        }


        executorService.shutdown();

        while(!executorService.awaitTermination(100, TimeUnit.MILLISECONDS)){
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("executorService has not stoped!");
        }



//        qpsAccumulate.printALL();
    }
}


