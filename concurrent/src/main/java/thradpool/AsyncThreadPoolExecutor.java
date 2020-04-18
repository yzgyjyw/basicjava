package thradpool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;

public class AsyncThreadPoolExecutor {

    private static final Logger LOGGER = LoggerFactory.getLogger(AsyncThreadPoolExecutor.class);

    private static final int DEFAULT_QUEUE_SIZE = 1000;

    private static final int DEFAULT_CORE_SIZE = 10;


    private static final ScheduledExecutorService scheduledExecutorService =
            new ScheduledThreadPoolExecutor(1, r -> {
                Thread t = new Thread(r);
                t.setName("async schedule executor monitor thread");
                return t;
            });


    private static final ThreadPoolExecutor threadPoolExecutor =
            new ThreadPoolExecutor(DEFAULT_CORE_SIZE, DEFAULT_CORE_SIZE, 0, TimeUnit.SECONDS, new LinkedBlockingQueue<>(DEFAULT_QUEUE_SIZE),
                    new ThreadFactory() {
                        int i = 0;

                        @Override
                        public Thread newThread(Runnable r) {
                            Thread t = new Thread(r);
                            t.setName("asyn executor " + i++);
                            return t;
                        }
                    },
                    ((r, executor) -> {
                        LOGGER.error("the async threadpool is full");
                    })
            );

    static {
        scheduledExecutorService.scheduleAtFixedRate(() -> {
            LOGGER.info("Thread Pool Total Task Count:" + threadPoolExecutor.getTaskCount());
        LOGGER.info("Thread Pool Has Completed Tasks Count:" + threadPoolExecutor.getCompletedTaskCount());
        LOGGER.info("Thread Pool Has Created Max Thread Num:" + threadPoolExecutor.getMaximumPoolSize());
        LOGGER.info("Thread Pool Threads Num :" + threadPoolExecutor.getPoolSize());
        LOGGER.info("Thread Pool Active Threads Num :" + threadPoolExecutor.getActiveCount());
    }, 0, 10, TimeUnit.SECONDS);
}

    public static void execute(Runnable runnable) {
        threadPoolExecutor.execute(runnable);
    }

    public static void shutdown() {
        threadPoolExecutor.shutdown();
        try {
            while (!threadPoolExecutor.awaitTermination(10, TimeUnit.MILLISECONDS)) {
                LOGGER.info("threadPoolExecutor is shutdowning");
            }
        } catch (Exception ex) {
            LOGGER.error("shutdown exception", ex);
        }

    }
}
