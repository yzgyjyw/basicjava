package distributedqps;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.ReentrantLock;

public class AckStatistics {

    private static java.util.Map<Long, AckAccumulatorItem> localCache = new ConcurrentHashMap<Long, AckAccumulatorItem>();

    private static Cache<Long, Long> ackStatisticCache = CacheBuilder.newBuilder()
            .expireAfterWrite(1000, TimeUnit.MILLISECONDS)
            .build();

    static JedisPool jedisPool;

    static {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        // 设置最大10个连接
        jedisPoolConfig.setMaxTotal(10);
        jedisPool = new JedisPool(jedisPoolConfig, "127.0.0.1");
    }

    static ScheduledExecutorService scheduleThreadPool;

    static {
        scheduleThreadPool = Executors.newScheduledThreadPool(1);
        scheduleThreadPool.scheduleAtFixedRate(() -> {
            try {
                localCache.forEach((appId, ackAccumulatorItem) -> {
                    if (System.currentTimeMillis() - ackAccumulatorItem.lastSaveTime > 5000) {
                        save2Redis(appId, ackAccumulatorItem);
                    }
                });

            } catch (Exception e) {
                System.out.println("schedule error");
            }
        }, 5000, 5000, TimeUnit.MILLISECONDS);
    }

    public static void incr(long appid) {
        AckAccumulatorItem accumulatorItem = localCache.computeIfAbsent(appid, appId -> new AckAccumulatorItem(10L));
        accumulatorItem.incr();
        save2Redis(appid, accumulatorItem);
    }

    private static void save2Redis(long appId, AckAccumulatorItem ackAccumulatorItem) {

        if (ackAccumulatorItem.lock.tryLock()) {
            try {
                long currentAckCount = ackAccumulatorItem.ackCount.get();
                if (currentAckCount > ackAccumulatorItem.maxAckCount) {
                    Jedis jedis = jedisPool.getResource();
                    if (ackAccumulatorItem.lastSaveTime == 0) {
                        jedis.set(String.valueOf(appId), String.valueOf(0), "NX", "PX", 100000);
                    }
                    Long incr = jedis.incrBy(String.valueOf(appId), currentAckCount);
                    jedis.close();
                    // redis中保存成功,更新本地该app的送达数
                    if (incr > 0) {
                        ackAccumulatorItem.ackCount.getAndAdd(0 - currentAckCount);
                    }
                }
            } finally {
                ackAccumulatorItem.lock.unlock();
            }
        }

    }

    public static long getAckStatistics(long appId) throws ExecutionException {
        return ackStatisticCache.get(appId, () -> {
            Jedis jedis = jedisPool.getResource();
            long ackCount = Long.valueOf(jedis.get(String.valueOf(appId)));
            System.out.println("load ackCount from redis of " + appId);
            jedis.close();
            return ackCount;
        });
    }
}


class AckAccumulatorItem {
    // 本地累积的送达数
    AtomicLong ackCount;

    // 最近一次保存到redis中的时间
    Long lastSaveTime;

    // 当达到这个上限时记到redis中
    Long maxAckCount;

    ReentrantLock lock;

    public AckAccumulatorItem(Long maxAckCount) {
        ackCount = new AtomicLong(0);
        lastSaveTime = 0L;
        lock = new ReentrantLock();
        this.maxAckCount = maxAckCount;
    }

    public long incr() {
        return ackCount.incrementAndGet();
    }

    @Override
    public String toString() {
        return ackCount.get() + "," + lastSaveTime;
    }

    public static void main(String[] args) {
        Set<Long> appId = new HashSet<>();
        System.out.println(appId.contains(null));
    }

}

