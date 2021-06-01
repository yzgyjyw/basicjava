import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ConsumerProducerLock {

    static List<Integer> list = new ArrayList<>();
    static int size = 10;

    static ReentrantLock lock = new ReentrantLock();

    static Condition empty = lock.newCondition();
    static Condition full = lock.newCondition();

    static AtomicInteger integer = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException {

        /*ThreadPoolExecutor consumer = new ThreadPoolExecutor(10, 10, 60, TimeUnit.SECONDS, new LinkedBlockingQueue<>(), new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread t = new Thread();
                t.setName("consumer");
//                t.setDaemon(false);
                return t;
            }
        });


        ThreadPoolExecutor producer = new ThreadPoolExecutor(10, 10, 60, TimeUnit.SECONDS, new LinkedBlockingQueue<>(), new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread t = new Thread();
                t.setName("consumer");
//                t.setDaemon(false);
                return t;
            }
        });*/

       /* for(int i=0;i<100;i++){
            consumer.submit(()->{
                Consumer.consumer();
            });
        }


        for(int i=0;i<100;i++){
            producer.submit(()->{
                Producer.produce(integer.addAndGet(1));
            });
        }*/

        new Thread(() -> {
            while (true) {
                Consumer.consumer();
            }
        }).start();

        new Thread(() -> {
            while (true) {
                Producer.produce(integer.incrementAndGet());
            }

        }).start();

        Thread.sleep(10000);
    }


    static class Consumer {

        public static void consumer() {


            try {
                lock.lock();

                while (list.size() <= 0) {
                    full.await();
                }

                System.out.println(list.remove(0));

                empty.signalAll();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }


        }

    }


    static class Producer {
        public static void produce(int i) {

            try {
                lock.lock();

                while (list.size() >= size) {
                    empty.await();
                }

                list.add(i);
                full.signalAll();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }


        }
    }

}
