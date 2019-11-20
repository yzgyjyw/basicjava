package thradpool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;

public class ShutdownThreadPool {

    private static final Logger LOGGER = LoggerFactory.getLogger(ShutdownThreadPool.class);


    private ExecutorService executor = Executors.newFixedThreadPool(3);

    public void stop() {
        executor.shutdown();
    }

    public void handle(Runnable runnable) {
        try {
            executor.execute(runnable);
            // 当线程池停止后，继续提交任务会执行拒绝策略，默认的拒绝策略就是抛出RejectedExecutionException异常
        } catch (RejectedExecutionException ex) {
            if (executor.isShutdown()) {
                LOGGER.warn("executor is shutdown");
            } else {
                LOGGER.error("task is refused");
            }
        }
    }


    public static void main(String[] args) {
        ShutdownThreadPool shutdownThreadPool = new ShutdownThreadPool();
        for (int i = 0; i < 10; i++) {
            shutdownThreadPool.handle(new Task());
        }
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            LOGGER.error("", e);
        }
        shutdownThreadPool.stop();
        for (int i = 0; i < 10; i++) {
            shutdownThreadPool.handle(new Task());
        }
    }
}


class Task implements Runnable {

    @Override
    public void run() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + "\tdone!");
    }
}