package com.jyw.lq.collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

public class DelayQueueDemo {

    private static final DelayQueue<QpsItem> qpsDelayQueue = new DelayQueue<>();
    private static final ExecutorService fixedLimitThreadPool = Executors.newFixedThreadPool(1);
    private static final Logger LOGGER = LoggerFactory.getLogger(DelayQueueDemo.class);

    static {
        fixedLimitThreadPool.execute(() -> {
            try {
                while (true) {
                    QpsItem qpsItem = qpsDelayQueue.take();
                    LOGGER.info("delay queue expired, {}", qpsItem);
                    qpsItem.isExceedInCurrentRounds = false;
                }
            } catch (Exception e) {
                LOGGER.error("revert local token cache pool error", e);
            }
        });
    }


    public static void main(String[] args) throws InterruptedException {

        Thread.sleep(1000);

        for (int i = 1; i < 10; i++) {
            qpsDelayQueue.offer(new QpsItem(i));
        }

        Thread.sleep(1000);
        System.out.println(qpsDelayQueue.size());

        QpsItem take = qpsDelayQueue.take();

        LOGGER.info("take {}", take);
        Thread.sleep(Integer.MAX_VALUE);

    }

    static class QpsItem implements Delayed {
        // 当前本地令牌的数量
        AtomicInteger localTokenQuantity;

        volatile long expireTime;

        // 本地允许存放的令牌的最大值
        int localTokenSize;

        int qps;

        // 本周期内token是否已经全部取完
        volatile boolean isExceedInCurrentRounds;

        ReentrantLock lock;

        private QpsItem(int expireTime) {
            this.expireTime = expireTime;
        }

        @Override
        public long getDelay(TimeUnit unit) {
            return unit.convert(expireTime - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
        }

        @Override
        public int compareTo(Delayed o) {
            return (int) (this.getDelay(TimeUnit.MILLISECONDS) - o.getDelay(TimeUnit.MILLISECONDS));
        }

        public QpsItem(AtomicInteger localTokenQuantity, long expireTime, int localTokenSize, int qps, boolean isExceedInCurrentRounds, ReentrantLock lock) {
            this.localTokenQuantity = localTokenQuantity;
            this.expireTime = expireTime;
            this.localTokenSize = localTokenSize;
            this.qps = qps;
            this.isExceedInCurrentRounds = isExceedInCurrentRounds;
            this.lock = lock;
        }
    }
}
