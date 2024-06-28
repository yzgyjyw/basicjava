package thradpool;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ScheduleThreadPool {

    public static void main(String[] args) throws InterruptedException {
        testScheduleShutdown();
    }

    public static void testScheduleShutdown(){
        ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(1);
        // 该变量控制线程池shutdown后,是否还需要执行线程池中的周期性任务
        // 需要注意的是与变量executeExistingDelayedTasksAfterShutdown区分开,该值控制的是非周期性任务在线程池shutdown后是否还需要执行线程池中的任务
        scheduledThreadPoolExecutor.setContinueExistingPeriodicTasksAfterShutdownPolicy(false);

        scheduledThreadPoolExecutor.scheduleAtFixedRate(() -> {
            System.out.println("schedule");
        }, 1000,1000, TimeUnit.MILLISECONDS);

//        Thread.sleep(1000);

        Runtime.getRuntime().addShutdownHook((new Thread(() -> scheduledThreadPoolExecutor.shutdown())));
    }

    public static void testTime(){
        Timer timer = new Timer("abc");

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                System.out.println("timer");
            }
        },0,1000);
    }


}
