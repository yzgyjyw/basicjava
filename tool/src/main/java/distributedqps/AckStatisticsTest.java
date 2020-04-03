package distributedqps;

import java.util.concurrent.*;

public class AckStatisticsTest {

    public static void main(String[] args) throws InterruptedException, ExecutionException {

        ThreadPoolExecutor executorService = new ThreadPoolExecutor(20,20,0L,TimeUnit.SECONDS,new LinkedBlockingQueue<>());

        for (int i = 0; i < 10000; i++) {
            executorService.execute(() -> {
                AckStatistics.incr(1L);
            });
        }

        for (int i = 0; i < 1000; i++) {
            executorService.execute(() -> {
                AckStatistics.incr(2L);
            });
        }

        for (int i = 0; i < 1000; i++) {
            executorService.execute(() -> {
                AckStatistics.incr(1L);
            });
        }

        /*executorService.shutdown();

        while (!executorService.awaitTermination(1000, TimeUnit.MILLISECONDS)) {
            System.out.println("has not completed");
        }*/

        System.out.println("end put");
        System.out.println("1L ackCount:" + AckStatistics.getAckStatistics(1L));
        System.out.println("2L ackCount:" + AckStatistics.getAckStatistics(2L));

        while(true){
            Thread.sleep(500);
            System.out.println("end sleep");
            System.out.println("1L ackCount:" + AckStatistics.getAckStatistics(1L));
            System.out.println("2L ackCount:" + AckStatistics.getAckStatistics(2L));
            for (int i = 0; i < 1000; i++) {
                executorService.execute(() -> {
                    AckStatistics.incr(1L);
                });
            }
        }

    }

}
