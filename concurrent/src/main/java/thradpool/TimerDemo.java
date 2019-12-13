package thradpool;

import com.sun.xml.internal.bind.v2.model.annotation.RuntimeAnnotationReader;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Timer的两个问题：
 *  1. 只有一个线程，当有执行时间较长的task时，会影响别的task的定时策略
 *  2. 当task抛出异常时，如果没有捕获，那么线程会直接退出，导致所有的定时任务都没法执行
 */
public class TimerDemo {
    public static void main(String[] args) {
        taskThrowException();
    }

    public static void taskThrowException(){
        // 一旦task抛出异常，就会导致TimeThread的run方法直接退出
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                int a = 1/0;
                System.out.println(System.currentTimeMillis()+"\t执行了");
            }
        },0L,1000L);

        try {
            Thread.sleep(Long.MAX_VALUE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void threadDelay(){
        Timer timer = new Timer();
        // 由于Timer中只存在一个线程，因此当有一个task执行所需的时间较长时，会影响另一个task的执行，这边shortTimeTask就不会每个1000ms执行一次，变成了每4s执行一次（固定延时策略）
        /*timer.schedule(new HighTimeTask(), 0L,1000L);
        timer.schedule(new ShortTimeTask(), 0L,1000L);*/

        // 由于Timer中只存在一个线程，因此当有一个task执行所需的时间较长时，会影响另一个task的执行，这边shortTimeTask就不会每个1000ms执行一次，变成了每8s连续执行2次（固定速率策略）
        timer.scheduleAtFixedRate(new HighTimeTask(), 0L,1000L);
        timer.scheduleAtFixedRate(new ShortTimeTask(), 0L,1000L);
    }
}


class HighTimeTask extends TimerTask {

    @Override
    public void run() {
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {

        }
        System.out.println(System.currentTimeMillis() + "\t" + Thread.currentThread().getName() + "\tHighTimeTask is done!");
    }
}

class ShortTimeTask extends TimerTask {

    @Override
    public void run() {
        /*try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {

        }*/
        System.out.println(System.currentTimeMillis() + "\t" + Thread.currentThread().getName() + "\tShortTimeTask is done!");
    }
}
