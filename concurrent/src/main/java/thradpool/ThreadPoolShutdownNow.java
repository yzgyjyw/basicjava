package thradpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPoolShutdownNow {
    public static void main(String[] args) throws InterruptedException {

        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(5);
        for(int i=0;i<10;i++) {
            fixedThreadPool.submit(new Runner());
        }
        fixedThreadPool.shutdownNow();
        //无论是shutdown还是shutdownNow都会再次提交都会抛出异常： java.util.concurrent.RejectedExecutionException
        try{
            fixedThreadPool.submit(new Runner());
        }catch (Exception e){

        }

        boolean terminated = fixedThreadPool.isTerminated();
        while(!terminated){
            terminated = fixedThreadPool.isTerminated();
            System.out.println(terminated);
            Thread.sleep(1000);
        }

    }
}
