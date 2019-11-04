package guardedsuspension;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Predicate;


// guarded suspension:保护性的暂停
// 用于异步转同步
public class GuardedObject<T> {

    //受保护的对象, 通常是某一个异步调用的结果(可以是rpc调用,也可以是mq的返回结果)
    T obj;

    final ReentrantLock lock = new ReentrantLock();
    final Condition done = lock.newCondition();

    final int timeout = 1;

    // 通常异步处理时,调用方一般是多线程处理的(意味着同一时刻可能有多个请求等待结果返回),这里面记录者某一个请求与GuardedObject之间的映射关系
    final static Map<Object, GuardedObject> gos = new ConcurrentHashMap<>();

    //根据request指定的key创建一个GuardedObject,并将其放入map中
    static <K> GuardedObject create(K key) {
        GuardedObject go = new GuardedObject();
        gos.put(key, go);
        return go;
    }

    // 在request获得response时,触发该request的guardedobject的onchanged方法,最终调用signalAll方法
    static <K, T> void fireEvent(K key, T obj) {
        GuardedObject go = gos.remove(key);
        if (go != null) {
            go.onChanged(obj);
        }
    }

    //
    T get(Predicate<T> p) {
        lock.lock();
        try {
            //使用while
            while (!p.test(obj)) {
                done.await(timeout, TimeUnit.SECONDS);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        return obj;
    }

    void onChanged(T obj) {
        lock.lock();
        try {
            this.obj = obj;
            done.signalAll();
        } finally {
            lock.unlock();
        }
    }
}
