package reentrantlock;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.StampedLock;

/**
 * ReadWriteLock 支持两种模式：一种是读锁，一种是写锁。而 StampedLock 支持三种模式，分别是：写锁、悲观读锁和乐观读。
 * 其中，写锁、悲观读锁的语义和 ReadWriteLock 的写锁、读锁的语义非常类似，允许多个线程同时获取悲观读锁，但是只允许一个线程获取写锁，写锁和悲观读锁是互斥的。
 * 不同的是：StampedLock 里的写锁和悲观读锁加锁成功之后，都会返回一个 stamp；然后解锁的时候，需要传入这个 stamp。
 */
public class StampedLockDemo {

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        Point point = new Point();
        while (true) {
            executorService.execute(() -> {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                point.move();
            });
            executorService.execute(() -> {
                System.out.println(point.getDistance());
            });
        }

    }

}

class Point {
    private int x;
    private int y;

    StampedLock stampedLock = new StampedLock();

    public void move() {
        long stamp = stampedLock.writeLock();
        try {
            x += 1;
            y += 1;
        } finally {
            stampedLock.unlock(stamp);
        }
    }

    //JDK官方使用示例，使用StampedLock时,先获取读锁(此时会返回1个stamped,在读后,在校验这个stamped,校验不成功则将乐观读锁升级为悲观读锁)
    public double getDistance() {
        //乐观读，乐观读没有加锁
        long stamp = stampedLock.tryOptimisticRead();
        int curX = x;
        int curY = y;
        //判断在读操作期间是否存在写操作
        if (!stampedLock.validate(stamp)) {
            System.out.println("乐观锁失败,转为悲观读锁");
            //存在写操作，升级为悲观读
            stamp = stampedLock.readLock();
            try {
                curX = x;
                curY = y;
            } finally {
                stampedLock.unlock(stamp);
            }
        }
        return Math.sqrt(curX * curX + curY * curY);
    }

}