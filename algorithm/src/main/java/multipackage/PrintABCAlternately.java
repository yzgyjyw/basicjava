package multipackage;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

// 问题：虽然是交替打印,但是有可能出现的是ACBACB 而不是ABC
public class PrintABCAlternately {
    public static void main(String[] args) throws InterruptedException {
        Lock lock = new ReentrantLock();

        Condition conditionA = lock.newCondition();
        Condition conditionB = lock.newCondition();
        Condition conditionC = lock.newCondition();

        Thread t1 = new Thread(()->{
            lock.lock();
            try{
                while (true){
                    System.out.println(Thread.currentThread().getName() + "\t" + "A");
                    try {
                        conditionB.signal();
                        conditionA.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }finally {
                lock.unlock();
            }
        });

        Thread t2 = new Thread(()->{
            lock.lock();
            try{
                while(true){
                    System.out.println(Thread.currentThread().getName() + "\t" + "B");
                    try {
                        conditionC.signal();
                        conditionB.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }finally {
                lock.unlock();
            }
        });

        Thread t3 = new Thread(()->{
            lock.lock();
            try{
                while(true){
                    System.out.println(Thread.currentThread().getName() + "\t" + "C");
                    try {
                        conditionA.signal();
                        conditionC.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }finally {
                lock.unlock();
            }
        });

        t1.start();
        t2.start();
        t3.start();

        Thread.sleep(Integer.MAX_VALUE);
    }




}
