package twothreadrelay;

import java.util.concurrent.CountDownLatch;

//模拟一个线程的执行必须依赖另一个线程的执行
public class TwoThreadRelayByCountDownLatch {
    public static void main(String[] args) {
        relayByCountDownLatch();
    }

    static void relayByCountDownLatch(){
        CountDownLatch countDownLatch = new CountDownLatch(1);
        Event event = new Event();
        new Thread1(event,countDownLatch).start();
        new Thread2(event,countDownLatch).start();
    }
}

class Thread1 extends Thread{


    private Event event;

    private CountDownLatch countDownLatch;

    public Thread1(Event event, CountDownLatch countDownLatch) {
        this.event = event;
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        event.setValue(1000L);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            countDownLatch.countDown();
        }
    }
}

class Thread2 extends Thread{

    private Event event;
    private CountDownLatch countDownLatch;

    public Thread2(Event event, CountDownLatch countDownLatch) {
        this.event = event;
        this.countDownLatch = countDownLatch;
    }


    @Override
    public void run() {
        try {
            countDownLatch.await();
            System.out.println(event.getValue());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}


