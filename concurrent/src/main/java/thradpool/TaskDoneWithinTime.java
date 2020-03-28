package thradpool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

public class TaskDoneWithinTime {

    private static final Logger LOGGER = LoggerFactory.getLogger(AsyncThreadPoolExecutor.class);

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        List<Future<Integer>> list = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            list.add(executorService.submit(new TaskDoneWithinTimeTask()));
        }
        // 5秒还没有得出结果的Future将直接丢弃
        Thread.sleep(5000);

        for (Future<Integer> future : list) {
            try {
                Integer integer = future.get(0L, TimeUnit.MILLISECONDS);
                System.out.println(integer);
            } catch (ExecutionException e) {
                LOGGER.error("Execution exception", e);
            } catch (TimeoutException e) {
                future.cancel(false);
            }
        }
    }


}

class TaskDoneWithinTimeTask implements Callable<Integer> {

    @Override
    public Integer call() {
        int time = new Random().nextInt(10000);
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {

        }
        return time;
    }
}
