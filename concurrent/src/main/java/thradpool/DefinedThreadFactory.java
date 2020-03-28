package thradpool;

import org.apache.log4j.spi.LoggerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Time;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

// 为线程池指定固定的线程工厂，并在打印线程的状态
public class DefinedThreadFactory {

    public static void main(String[] args) throws InterruptedException {
        ThreadPoolExecutor executor = new MyStatisticThreadPool(10, 10, 0, TimeUnit.SECONDS, new ArrayBlockingQueue<>(10), new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                return new MyThread(r);
            }
        });

        for (int i = 0; i < 20; i++) {
            executor.submit(() -> {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }

        executor.shutdown();
        while (executor.awaitTermination(1000, TimeUnit.MICROSECONDS)) {
            Thread.sleep(1000);
        }
    }
}

class MyStatisticThreadPool extends ThreadPoolExecutor {

    private static final Logger LOGGER = LoggerFactory.getLogger(MyStatisticThreadPool.class);

    private final ThreadLocal<Long> startTime = new ThreadLocal<>();
    private final AtomicInteger numTasks = new AtomicInteger();
    private final AtomicLong totalTime = new AtomicLong();

    public MyStatisticThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }

    public MyStatisticThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory);
    }

    public MyStatisticThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, handler);
    }

    public MyStatisticThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory, RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
    }

    @Override
    protected void beforeExecute(Thread t, Runnable r) {
        super.beforeExecute(t, r);
        LOGGER.info("before execute, thread name : {}", Thread.currentThread().getName());
        startTime.set(System.currentTimeMillis());
    }

    @Override
    protected void afterExecute(Runnable r, Throwable t) {
        try {

            totalTime.addAndGet(System.currentTimeMillis() - startTime.get());
            numTasks.incrementAndGet();
            LOGGER.info("thread name : {}, numTasks : {}, totalTime : {}", Thread.currentThread().getName(), numTasks.get(), totalTime.get());
        } finally {
            super.afterExecute(r, t);
        }
    }

    @Override
    protected void terminated() {
        try {
            LOGGER.info("Terminated: avg-time = {}", totalTime.get() / numTasks.get());
        } finally {
            super.terminated();
        }
    }
}

class MyThread extends Thread {

    private static final Logger LOGGER = LoggerFactory.getLogger(MyThread.class);

    private static AtomicInteger threadCount = new AtomicInteger(0);
    private static final String DEFAULT_NAME = "thread_name";
    private static AtomicInteger threadAlive = new AtomicInteger(0);

    static {
        Executors.newScheduledThreadPool(1).scheduleWithFixedDelay(() -> {
            LOGGER.info("thraedCount : {}, threadAlive : {}", threadCount, threadAlive);
        }, 0, 1000, TimeUnit.MICROSECONDS);
    }

    public MyThread(Runnable runnable) {
        this(runnable, DEFAULT_NAME);
    }

    public MyThread(Runnable runnable, String name) {
        super(runnable, name + "_" + threadCount.incrementAndGet());
        setUncaughtExceptionHandler((t, e) -> LOGGER.error("thread : {} is error!", t, e));
    }

    @Override
    public void run() {
        try {
            threadAlive.incrementAndGet();
            super.run();
        } finally {
            threadAlive.decrementAndGet();
        }
    }

    public static AtomicInteger getThreadCount() {
        return threadCount;
    }

    public static AtomicInteger getThreadAlive() {
        return threadAlive;
    }
}
