package multipackage;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class PrintABCAlternately2 {
    static Lock lock = new ReentrantLock();
    static Condition conditionA = lock.newCondition();
    static Condition conditionB = lock.newCondition();
    static Condition conditionC = lock.newCondition();

    static int num = 0;

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            lock.lock();
            try {
                while (true) {
                    while (num % 3 != 0) {
                        try {
                            conditionB.signal();
                            conditionA.await();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.println(Thread.currentThread().getName() + "\t" + "A");
                    num++;
                }
            } finally {
                lock.unlock();
            }

        });

        Thread t2 = new Thread(() -> {
            lock.lock();
            try {
                while (true) {
                    while (num % 3 != 1) {
                        try {
                            conditionC.signal();
                            conditionB.await();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.println(Thread.currentThread().getName() + "\t" + "B");
                    num++;
                }
            } finally {
                lock.unlock();
            }
        });

        Thread t3 = new Thread(() -> {
            lock.lock();
            try {
                while (true) {
                    while (num % 3 != 2) {
                        try {
                            conditionA.signal();
                            conditionC.await();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.println(Thread.currentThread().getName() + "\t" + "C");
                    num++;
                }
            } finally {
                lock.unlock();
            }

        });

        t1.start();
        t2.start();
        t3.start();

        Thread.sleep(Integer.MAX_VALUE);
    }
}
