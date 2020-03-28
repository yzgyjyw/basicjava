package threadloca;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// 记住threadlocal在线程内共享数据，即在同一个线程的生命周期内threadlocal内的数据是共享的，在使用线程池的时候尤其需要注意
public class ThreadLocalDemo {

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        for (int i = 0; i < 10; i++) {
            executorService.submit(new ThreadLocalTask());
        }
    }

}


class ThreadLocalTask implements Runnable {

    private static ThreadLocal<StringBuilder> stringBuilderThreadLocal = ThreadLocal.withInitial(StringBuilder::new);

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName()+"\tbegin...");
        stringBuilderThreadLocal.get().append(Thread.currentThread().getName())
                .append("\t")
                .append(System.currentTimeMillis())
                .append("\n");

        System.out.println(stringBuilderThreadLocal.get().toString());

//        stringBuilderThreadLocal.remove();
    }
}