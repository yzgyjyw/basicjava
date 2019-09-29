import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

/**
 * 对象池
 */
public class ObjectPool {
    //使用 阻塞队列保存对象池
    private final ArrayBlockingQueue<InputSaleMapDO> pool;
    //信号量
    private final Semaphore sem;

    /**
     * 初始化对象池
     *
     * @param size 池大小
     */
    public ObjectPool(int size) {
        pool = new ArrayBlockingQueue<>(size);
        sem = new Semaphore(size);
        for (int i = 0; i < size; i++) {
            InputSaleMapDO inputSaleMapDO = new InputSaleMapDO();
            inputSaleMapDO.setId(i);
            pool.add(inputSaleMapDO);
        }
    }

    //利用对象池的对象调用 function
    public Long run(Function<InputSaleMapDO, Long> function) throws InterruptedException {
        InputSaleMapDO obj = null;
        sem.acquire();
        try {
            Thread.sleep(3000);
            obj = pool.poll();
            return function.apply(obj);
        } finally {
            pool.add(obj);
            sem.release();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ObjectPool objectPool = new ObjectPool(2);

        //模拟十个线程同时访问
        CountDownLatch countDownLatch = new CountDownLatch(1);
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                try {
                    objectPool.run(f -> {
                        System.out.println(f);
                        return f.getId();
                    });
                    countDownLatch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
        countDownLatch.countDown();
        System.out.println("main thread");
        TimeUnit.SECONDS.sleep(10);
    }
}

class InputSaleMapDO {
    long id;

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

