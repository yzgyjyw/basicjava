package atomic;

import redis.clients.jedis.Jedis;

import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.ReentrantLock;

public class AtomicTest {

    public static void main(String[] args) {
        AtomicLong atomicLong = new AtomicLong();
        long currentPermitsNum = atomicLong.get();


    }


}

class LocalPermits {
    long permits;
    long time;

    public synchronized boolean get() {
        if (System.currentTimeMillis() - time > 1000) {
            return update("1");
        }

        long currentPermits = permits;
        if (currentPermits > 0) {
            permits--;
            return true;
        }

        if (currentPermits == 0) {
            //去redis中获取
            return update("1");
        } else {
            return false;
        }
    }

    public boolean update(String appid) {
        // TODO 从redis中获取数据
        Jedis jedis = new Jedis("127.0.0.1");
        Long redisPermitsTtl = jedis.pttl(appid);
        if (redisPermitsTtl > 0) {
            long currentpermits = 10;
            Long accumulateResult = jedis.decrBy(appid, currentpermits);
            if (accumulateResult < 0 && accumulateResult > -10) {
                currentpermits = accumulateResult + 10;
            }
            this.permits = currentpermits;
            this.time = System.currentTimeMillis() + redisPermitsTtl;
            if (permits > 0) {
                permits--;
                return true;
            }
        } else {





        }

        this.time = System.currentTimeMillis();
        this.permits = 10;
        return true;
    }
}