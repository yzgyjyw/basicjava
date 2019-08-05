package thradpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ThreadPoolShutDown {

    //优雅的关闭线程池
    //shutdown:

    public static void main(String[] args) throws InterruptedException {
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(5);

        for(int i=0;i<10;i++) {
            fixedThreadPool.submit(new Runner());
        }

        Thread.sleep(0);
        fixedThreadPool.shutdown();
//        fixedThreadPool.submit(new Runner());
        boolean terminated = fixedThreadPool.isTerminated();

        fixedThreadPool.awaitTermination(1, TimeUnit.SECONDS);
        System.out.println(terminated);
        while(!terminated){
            terminated = fixedThreadPool.isTerminated();
            System.out.println(terminated);
            Thread.sleep(1000);
        }

    }

}
