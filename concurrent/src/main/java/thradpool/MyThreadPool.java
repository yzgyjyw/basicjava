package thradpool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class MyThreadPool {

    private BlockingQueue<Runnable> taskQueue;

    private List<Thread> threads = new ArrayList<>();

    public MyThreadPool(int coreSize, BlockingQueue<Runnable> taskQueue) {
        this.taskQueue = taskQueue;
        for (int i = 0; i < coreSize; i++) {
            Worker worker = new Worker();
            worker.start();
            threads.add(worker);
        }
    }

    public void execute(Runnable runnable) {
        taskQueue.add(runnable);
    }

    class Worker extends Thread {

        @Override
        public void run() {
            try {
                while(true){
                    Runnable runnable = taskQueue.take();
                    runnable.run();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }


    public static void main(String[] args) {
        LinkedBlockingQueue<Runnable> taskQueue = new LinkedBlockingQueue<>();
        MyThreadPool pool = new MyThreadPool(10, taskQueue);

        for (int i = 0; i < 20; i++) {
            pool.execute(() -> {
                try {
                    Thread.sleep(1000);
                    System.out.println(System.currentTimeMillis());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}
