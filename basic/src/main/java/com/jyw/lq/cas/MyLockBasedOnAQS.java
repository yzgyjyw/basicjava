package com.jyw.lq.cas;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.stream.IntStream;

public class MyLockBasedOnAQS {


    static class Sync extends AbstractQueuedSynchronizer {
        @Override
        protected boolean tryAcquire(int arg) {
            if (compareAndSetState(0, 1)) {
                setExclusiveOwnerThread(Thread.currentThread());
                return true;
            }
            return false;
        }

        @Override
        protected boolean tryRelease(int arg) {
            setExclusiveOwnerThread(null);
            setState(0);
            return true;
        }
    }

    private final Sync sync = new Sync();

    public void lock() {
        sync.acquire(1);
    }

    public void unlock() {
        sync.release(1);
    }

    private static int count = 0;

    public static void main(String[] args) throws InterruptedException {
        MyLockBasedOnAQS lock = new MyLockBasedOnAQS();
        IntStream.range(0, 100000).forEach(i -> new Thread(() -> {
            lock.lock();
            try {
                count++;
            } finally {
                lock.unlock();
            }

        }).start());

        Thread.sleep(1000);
        System.out.println(count);
    }

}
