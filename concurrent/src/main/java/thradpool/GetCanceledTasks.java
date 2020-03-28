package thradpool;

import com.google.common.collect.Sets;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.*;

// 通常情况下，我们在使用shutdownNow()方法关闭一个executot时,会给所有正在运行的任务发送一个中断请求，同时会将尚在队列中还没有被调度的任务返回出来；
// 但是我们却无法得到正在运行但是被中断的任务
public class GetCanceledTasks {


    public static void main(String[] args) throws InterruptedException {
        getCanceledTask();
    }

    public static void testGetWaitQueueTask() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 30; i++) {
            executorService.submit(() -> {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return 20;
            });
        }

        Thread.sleep(100);
        //这边的返回结果是处于等待队列中还没有运行的任务
        List<Runnable> runnables = executorService.shutdownNow();

        System.out.println(1);
    }

    public static void getCanceledTask() throws InterruptedException {
        ExecutorServiceGetCanceledTasks executorService = new ExecutorServiceGetCanceledTasks(Executors.newFixedThreadPool(10));
        for (int i = 0; i < 30; i++) {
            executorService.submit(() -> {
                while (true) {
                    if (Thread.currentThread().isInterrupted()) {
                        return 10;
                    }
                    System.out.println(System.currentTimeMillis());
                }

                /*try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    //在抛出InterruptedException后,会清除中断标志位,因此在捕获该异常时，一般需要重置中断，好让外部的线程知道本线程的中断状态，进行后续的处理
                    //这边需要再次设置中断
                    Thread.currentThread().interrupt();
                }*/
            });
        }

        Thread.sleep(100);
        //这边的返回结果是处于等待队列中还没有运行的任务
        List<Runnable> runnables = executorService.shutdownNow();
        List<Runnable> canceledTasks = new ArrayList<>();
        if (executorService.awaitTermination(1000, TimeUnit.MILLISECONDS)) {
            canceledTasks.addAll(executorService.getCanceledTasks());
        }

        System.out.println(1);
    }

}

class ExecutorServiceGetCanceledTasks extends AbstractExecutorService {
    private ExecutorService executorService;
    private Set<Runnable> canceledTasks = Sets.newConcurrentHashSet();

    public ExecutorServiceGetCanceledTasks(ExecutorService executorService) {
        this.executorService = executorService;
    }

    public List<Runnable> getCanceledTasks() {
        if (!executorService.isTerminated()) {
            throw new RuntimeException("线程池尚未停止");
        }
        return new ArrayList<>(canceledTasks);
    }

    @Override
    public void execute(Runnable command) {
        executorService.execute(() -> {
            try {
                command.run();
            } finally {
                if (executorService.isShutdown() && Thread.currentThread().isInterrupted()) {
                    canceledTasks.add(command);
                }
            }
        });
    }

    //将ExecutorServiceGetCanceledTasks的其它方法全部委托给executorService对象

    @Override
    public void shutdown() {
        executorService.shutdown();
    }

    @Override
    public List<Runnable> shutdownNow() {
        return executorService.shutdownNow();
    }

    @Override
    public boolean isShutdown() {
        return executorService.isShutdown();
    }

    @Override
    public boolean isTerminated() {
        return executorService.isTerminated();
    }

    @Override
    public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
        return executorService.awaitTermination(timeout, unit);
    }
}