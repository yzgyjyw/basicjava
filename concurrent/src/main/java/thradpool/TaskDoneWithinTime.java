package thradpool;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class TaskDoneWithinTime {

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        List<Future<Integer>> list = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            list.add(executorService.submit(new TaskDoneWithinTimeTask()));
        }
    }


}

class TaskDoneWithinTimeTask implements Callable<Integer> {
    ThreadLocal<Random> randomThreadLocal = new ThreadLocal<>();

    public TaskDoneWithinTimeTask() {
        randomThreadLocal.set(new Random());
    }

    @Override
    public Integer call() {
        int time = randomThreadLocal.get().nextInt(10000);
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {

        }
        return time;
    }
}
