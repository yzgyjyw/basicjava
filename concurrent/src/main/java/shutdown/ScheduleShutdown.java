package shutdown;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ScheduleShutdown {
    static ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

    public static void main(String[] args) {

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            executorService.shutdown();
            while (!executorService.isTerminated()) {
                try {
                    executorService.awaitTermination(1, TimeUnit.MILLISECONDS);
                } catch (InterruptedException ignore) {
                }
            }
        }));

        executorService.scheduleAtFixedRate(() -> System.out.println(System.currentTimeMillis()), 1,1, TimeUnit.SECONDS);
    }

}
