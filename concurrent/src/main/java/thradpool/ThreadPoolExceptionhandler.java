package thradpool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;

/*
* 总结：使用setUnCaughtExceptionHandler和afterExecute，会导致当前线程抛出异常直接退出，下次有任务还需要创建一个新的线程
* 虽然线程池会创建一个新的工作线程，但是如果这个步骤反复执行，效率自然会下降很多。
*
* 针对submit：setUnCaughtExceptionHandler和afterExecute，那么在进行submit的时候,任务会被封装进 FutureTask 类中，然后最终工作线程执行的是 FutureTask 中的 run 方法
    如果任务抛出异常,会被 setException 方法赋给代表执行结果的 outcome 变量,而不会继续抛出。因此，UncaughtExceptionHandler 也没有机会处理
*
* 所以针对submit方法，我们必须捕获future.get抛出的异常以进行异常处理
* */
public class ThreadPoolExceptionhandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(ThreadPoolExceptionhandler.class);

    public static void main(String[] args) throws Exception {
//        exception();
//        method1();
//        method2();
//        method3();
        method4();
    }


    //异常会被吞噬
    static void exception() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        executorService.execute(() -> {
            System.out.println("thread-name:" + Thread.currentThread().getName());
            int a = 10 / 0;
        });

        Thread.sleep(10000);
    }


    //方案一:直接将整个业务代码catch住,避免将异常抛给线程池
    static void method1() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        executorService.execute(() -> {
            try {
                LOGGER.info("thread-name:" + Thread.currentThread().getName());
                int a = 10 / 0;
            } catch (Throwable throwable) {
                LOGGER.error("{}", throwable);
            }
        });

        executorService.shutdown();
        Thread.sleep(10);
    }

    //方案二:自定义线程池，重写afterExecute方法
    static void method2() {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1, 1, 0, TimeUnit.SECONDS, new ArrayBlockingQueue<>(10)) {
            @Override
            protected void afterExecute(Runnable r, Throwable t) {
                super.afterExecute(r, t);
                if (t != null) {
                    LOGGER.error("afterExecute:", t);
                }
            }
        };

        //使用submit提交的时候，必须通过get来捕获异常
        threadPoolExecutor.execute(() -> {
            int a = 10 / 0;
        });
    }

    //方案三：设置thread的unCaughtExceptionHandler
    static void method3() {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1, 1, 0, TimeUnit.SECONDS, new ArrayBlockingQueue<>(10), new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread thread = new Thread(r);
                thread.setName(System.currentTimeMillis() + "");
                thread.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
                    @Override
                    public void uncaughtException(Thread t, Throwable e) {
                        LOGGER.error(t.getName(), e);
                    }
                });
                return thread;
            }
        });
        threadPoolExecutor.execute(() -> {
            int a = 1 / 0;
        });
    }


    //方案四:使用submit提交任务
    static void method4() {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1, 1, 0, TimeUnit.SECONDS, new ArrayBlockingQueue<>(10));

        Future<?> future = threadPoolExecutor.submit(() -> {
            int a = 1 / 0;
        });

        try {
            future.get();
        } catch (InterruptedException e) {
            LOGGER.error("",e);
        } catch (ExecutionException e) {
            LOGGER.error("",e);
        }
    }


}
