package thradpool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ThreadExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(ThreadExceptionHandler.class);

    public static void main(String[] args) {
        uncaughtExceptionHandler();
//        exception();
    }

    static void exception() {
        //执行该线程方法，虽然可以在控制台看到异常输出，但是在实践中基本不会使用控制台作为日志文件,这样下面代码的异常将会丢失
        new Thread(() -> {
            System.out.println(1 / 0);
        }).start();
    }


    static void tryCatch() {
        new Thread(() -> {
            try {
                System.out.println(1 / 0);
            } catch (Exception e) {
                LOGGER.error("", e);
            }
        }).start();
    }

    //线程在执行的过程中如果抛出异常需要使用uncaughtExceptionHandler来捕获
    static void uncaughtExceptionHandler() {
        Thread thread = new Thread(() -> {
            int a = 1 / 0;
        });
        thread.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                LOGGER.error(t.getName() + "\t" + e);
            }
        });
        thread.start();
    }

}
