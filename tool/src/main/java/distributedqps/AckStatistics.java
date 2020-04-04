package distributedqps;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author jiangyinwei
 */
public class AckStatistics {

    private static final long MILLSECONDS_DAY = 1000 * 60 * 60 * 24;

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
                // 获取到所有的appId，然后删除之前记录的键值对
            } catch (Exception e) {
                System.out.println("schedule error");
            }
        }, getDateStartTime(1) - System.currentTimeMillis(), MILLSECONDS_DAY, TimeUnit.MILLISECONDS);
    }

    public static void incr(long appid) {
        AckAccumulatorItem accumulatorItem = localCache.computeIfAbsent(appid, appId -> new AckAccumulatorItem(10L));
        accumulatorItem.incr();
        save2Redis(appid, accumulatorItem);
    }

    private static void save2Redis(long appId, AckAccumulatorItem ackAccumulatorItem) {
        long currentAckCount = ackAccumulatorItem.ackCount.get();
        if (currentAckCount < ackAccumulatorItem.maxAckCount) {
            return;
        }

        if (ackAccumulatorItem.lock.tryLock()) {
            try {
                currentAckCount = ackAccumulatorItem.ackCount.get();
                if (currentAckCount > ackAccumulatorItem.maxAckCount) {
                    Long incr = jedisPool.getResource().hincrBy(String.valueOf(appId), String.valueOf(getDateStartTime(0)), currentAckCount);
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

    private static long getDateStartTime(int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, day);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTimeInMillis();
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

