package thradpool;

import java.util.concurrent.*;

public class SubmitAttention {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1, 1, 0L, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(1), new ThreadPoolExecutor.DiscardPolicy());

        Future<String> taskOne = threadPoolExecutor.submit(() -> {
            Thread.sleep(1000);
            return "1";
        });

        Future<String> taskTwo = threadPoolExecutor.submit(() -> {
            Thread.sleep(1000);
            return "2";
        });


        Future<String> taskThree = threadPoolExecutor.submit(() -> {
            Thread.sleep(1000);
            return "3";
        });


        System.out.println("taskOne:\t" + taskOne.get());
        System.out.println("taskTwo:\t" + taskTwo.get());
        System.out.println("taskThree:\t" + taskThree.get());

        threadPoolExecutor.shutdown();
        threadPoolExecutor.awaitTermination(10, TimeUnit.SECONDS);

    }
}
