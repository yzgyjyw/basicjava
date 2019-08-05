package reentrantlock;

import java.util.concurrent.locks.ReentrantLock;

public class Interruptible {
    public static void main(String[] args) throws InterruptedException {
        ReentrantLock lock = new ReentrantLock();

        Thread thread1 = new Thread(new Runner1(lock));
        Thread thread2 = new Thread(new Runner2(lock));

        thread1.start();
        Thread.sleep(100);
        thread2.start();
        thread2.interrupt();
    }
}

class Runner1 implements Runnable{
    ReentrantLock lock;

    public Runner1(ReentrantLock lock) {
        this.lock = lock;
    }

    @Override
    public void run() {
        try{
            lock.lock();
            System.out.println(Thread.currentThread().getName()+"\tenter lock");
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}

class Runner2 implements Runnable{
    ReentrantLock lock;

    public Runner2(ReentrantLock lock) {
        this.lock = lock;
    }
    @Override
    public void run() {
        try{
            try {
                //优先响应中断
                lock.lockInterruptibly();
                System.out.println(Thread.currentThread().getName()+"\tenter lock");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }finally {
            lock.unlock();
        }
    }
}