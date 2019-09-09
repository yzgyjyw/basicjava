package reentrantlock;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.Function;

public class ReadWriteLockCache {

    public static void main(String[] args) {
        Cache<Long, String> cache = new Cache<>();
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        while (true) {
            executorService.execute(() -> {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Random random = new Random();
                System.out.println(cache.get(Long.valueOf(random.nextInt(10)), l -> String.valueOf(l + "\t" + System.currentTimeMillis())));
            });
        }

    }


}

class Cache<K, V> {
    private Map<K, V> map = new HashMap<>();
    private ReadWriteLock lock = new ReentrantReadWriteLock();

    public V get(K key, Function<K, V> func) {
        V value = null;
        try {
            lock.readLock().lock();
            value = map.get(key);
        } finally {
            lock.readLock().unlock();
        }

        if (value != null) {
            return value;
        }

        try {
            lock.writeLock().lock();
            //再次检查key针对的value是否为null，类似于单例模式中的双重检测
            value = map.get(key);
            if (value != null) {
                return value;
            }
            map.put(key, func.apply(key));
            return map.get(key);
        } finally {
            lock.writeLock().unlock();
        }
    }
}