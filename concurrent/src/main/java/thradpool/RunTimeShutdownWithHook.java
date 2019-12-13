package thradpool;

import org.apache.commons.lang3.concurrent.BasicThreadFactory;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class RunTimeShutdownWithHook {


    public static void main(String[] args) {

        ExecutorService executorService = Executors.newFixedThreadPool(10,new BasicThreadFactory.Builder().namingPattern("thirdparty-retry-later-%d").daemon(true).build());

        for(int i=0;i<100;i++){
            executorService.submit(()->{
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(System.currentTimeMillis());
            });
        }


        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {

                executorService.shutdown();
                try {
                    executorService.awaitTermination(1,TimeUnit.SECONDS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });

    }

}
