package com.jyw.accumulate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;

// 先在本地累加，然后写到redis中
public class QpsAccumulate {

    private java.util.Map<Long, QpsItem> localCache = new ConcurrentHashMap<Long, QpsItem>();

    ScheduledExecutorService scheduleThreadPool;

    {
        scheduleThreadPool = Executors.newScheduledThreadPool(1);
        scheduleThreadPool.scheduleAtFixedRate(() -> {
            flushALL();
        }, 1000, 1000, TimeUnit.MILLISECONDS);
    }

    public void incr(long appid) {
        QpsItem qpsItem = localCache.computeIfAbsent(appid, appId -> new QpsItem());
        qpsItem.incr();
    }

    public void flushALL() {
        localCache.forEach((appid, qpsItem) -> {
            if (System.currentTimeMillis() - qpsItem.times > 1000) {
                long localCount = qpsItem.flush();
                System.out.println(appid + ", " + localCount + ", " + System.currentTimeMillis());
            }
        });
    }

    public void printALL() {
        localCache.forEach((appid, qpsItem) -> {
            System.out.println(appid + "," + qpsItem);
        });
    }
}


class QpsItem {
    private Logger LOGGER = LoggerFactory.getLogger(QpsItem.class);

    AtomicLong count;
    Long times;

    public QpsItem() {
        count = new AtomicLong(0);
        times = 0L;
    }

    public void incr() {
        count.incrementAndGet();
    }

    public long flush() {
        long localCount = count.getAndSet(0L);
        times = System.currentTimeMillis();
        return localCount;
    }

    @Override
    public String toString() {
        return count.get() + "," + times;
    }
}

