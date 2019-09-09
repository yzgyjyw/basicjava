package reentrantlock;

import java.sql.SQLOutput;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.function.Function;

public class SemaphoreLimiting {

    public static void main(String[] args) throws InterruptedException {
        ObjPool<Long, String> objPool = new ObjPool<Long, String>(2, 2L);

        ExecutorService executorService = Executors.newFixedThreadPool(10);
        while(true){
            executorService.execute(()->{
                try {
                    objPool.exec((l) -> {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        System.out.println(System.currentTimeMillis() + "\t" + l);
                        return l.toString();
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
    }
}


class ObjPool<T, R> {
    //池子中存放资源
    final List<T> pool;

    final Semaphore semaphore;

    /**
     * @param size
     * @param t
     */
    ObjPool(int size, T t) {
        pool = new Vector<>(size);
        for (int i = 0; i < size; i++) {
            pool.add(t);
        }
        semaphore = new Semaphore(size);
    }

    //利用对象池调用fuc
    R exec(Function<T, R> func) throws InterruptedException {
        T t = null;
        try {
            semaphore.acquire();
            t = pool.remove(0);
            return func.apply(t);
        } finally {
            pool.add(t);
            semaphore.release();
        }
    }
}