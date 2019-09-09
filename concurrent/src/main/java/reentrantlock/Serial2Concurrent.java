package reentrantlock;

//对账系统

import java.util.Vector;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 背景：1. 订单库，派单库，检验是否一致
 * 旧有逻辑：串行的拿取订单A和派单A，check
 *
 * CyclicBarrier与CountDownLatch的区别：
 *  CycliBarrier 1.适用于多个线程互相等待 2.其计数器在结束后，可以循环使用 3.在计数器满后可以指定执行相关的动作
 *  CountDownLatch 1.适用于一个线程等待其它线程
 * <p>
 * 可以并行
 */
public class Serial2Concurrent {

    //订单队列
    static Vector<String> pos = new Vector<>();

    //派单队列
    static Vector<String> dos = new Vector<>();

    public static void main(String[] args) {

        ExecutorService executorService = Executors.newFixedThreadPool(1);

        CyclicBarrier cyclicBarrier = new CyclicBarrier(2, () -> {
            //这边必须新开启一个线程执行模拟对账的逻辑，因为cylicbarrier的后续操作是在最后一个await的线程上执行的，这样就不符合本场景的并行策略了
            executorService.execute(()->{
                //模拟对账
                System.out.println(pos.remove(0) + "\t" + dos.remove(0));
            });
        });


        Thread queryPOrderThread = new Thread() {
            private int count;

            @Override
            public void run() {
                while (true){
                    try {
                        Thread.sleep(1000);
                        pos.add("porder" + String.valueOf(count++));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }finally {
                        try {
                            cyclicBarrier.await();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (BrokenBarrierException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        };

        Thread queryDOrderThread = new Thread() {
            private int count;

            @Override
            public void run() {
                while (true){
                    try {
                        Thread.sleep(1000);
                        dos.add("dorder" + String.valueOf(count++));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }finally {
                        try {
                            cyclicBarrier.await();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (BrokenBarrierException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        };

        queryDOrderThread.start();
        queryPOrderThread.start();


    }


}