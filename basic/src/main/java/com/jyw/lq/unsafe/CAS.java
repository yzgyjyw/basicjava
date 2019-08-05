package com.jyw.lq.unsafe;

import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class CAS {

    public static void main(String[] args) throws InterruptedException {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(20, 20, 0, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(100));
        Counter counter = new Counter();
        for (int i = 0; i < 200; i++) {
            threadPoolExecutor.submit(() -> {
                counter.increment();
            });
        }
        Thread.sleep(1000);
        System.out.println(counter.getCount());
    }

}

class Counter {
    private volatile int count;

    private static long offset;
    private static Unsafe unsafe;

    static {
        try {
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            unsafe = (Unsafe) field.get(null);

            offset = unsafe.objectFieldOffset(Counter.class.getDeclaredField("count"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getCount() {
        return this.count;
    }

    public void increment() {
        int before = count;
        while (!unsafe.compareAndSwapInt(this, offset, before, before + 1)) {
            before = count;
        }
    }
}