package thradpool;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

//需要注意当有关联的线程需要在不同的线程池中
public class ThreadPoolDeadLock {

    public static void main(String[] args) {

        ExecutorService executor = Executors.newFixedThreadPool(2);

        for(int i=0;i<2;i++){
            System.out.println("第一阶段开始执行");
            executor.execute(()->{
                CountDownLatch countDownLatch = new CountDownLatch(2);

                //第二阶段的任务是由与执行第一个任务相同的executor执行的,但是第一阶段的任务还没有执行完毕，导致第二阶段永远不会被执行，同时第1阶段又依赖于第二阶段，造成死锁
                executor.execute(()->{
                    System.out.println("第二阶段");
                    countDownLatch.countDown();
                });

                try {
                    countDownLatch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println("第一阶段执行完毕");
            });
        }


    }

}
