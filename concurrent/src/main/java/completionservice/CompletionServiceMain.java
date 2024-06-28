package completionservice;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CompletionServiceMain {

    public static void main(String[] args) throws InterruptedException, ExecutionException {

        ExecutorService executorService = Executors.newFixedThreadPool(3);

        ExecutorCompletionService<String> completionService = new ExecutorCompletionService<>(executorService);

        completionService.submit(() -> {
            Thread.sleep(1000);
            return "string1";
        });


        completionService.submit(() -> {
            Thread.sleep(100);
            return "string2";
        });


        completionService.submit(() -> {
            Thread.sleep(2000);
            return "string3";
        });

        for (int i = 0; i < 3; i++) {
            // 异步执行三个任务,哪个任务先执行完了,就处理哪个任务返回的结果,不会存在第一个任务执行较慢,阻塞其它先执行完毕任务的处理
            String r = completionService.take().get();

            System.out.println(r);
        }
    }
}
