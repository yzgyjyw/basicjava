package thradpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolExit {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(1);

        long start = System.currentTimeMillis();
        for (int i = 0; i <= 5; i++) {
            fixedThreadPool.execute(() -> {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(1);
            });
        }

        fixedThreadPool.shutdown();
        while (!fixedThreadPool.awaitTermination(1, TimeUnit.SECONDS)) {
            System.out.println("线程池仍在执行");
        }
        System.out.println("共执行的时长:" + (System.currentTimeMillis() - start));

    }
}
