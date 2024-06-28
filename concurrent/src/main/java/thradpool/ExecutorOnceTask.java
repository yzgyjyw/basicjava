package thradpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

// 只执行1次的任务
public class ExecutorOnceTask {

    public static void main(String[] args) throws InterruptedException {
       /* ExecutorService executorService = Executors.newFixedThreadPool(10);
        try {
            for (int i = 0; i < 10; i++) {
                executorService.execute(() -> {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(100);
                });
            }
        } finally {
            executorService.shutdown();
            while (!executorService.awaitTermination(1, TimeUnit.MILLISECONDS)) {
                System.out.println("正在关闭");
            }
        }
        System.out.println("result");*/

        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleWithFixedDelay(()->{
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(System.currentTimeMillis());

            },0,1,TimeUnit.SECONDS);

        Thread.sleep(10000000);
    }

}
