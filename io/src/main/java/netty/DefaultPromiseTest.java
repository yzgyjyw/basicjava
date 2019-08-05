package netty;

import io.netty.util.concurrent.*;

public class DefaultPromiseTest {
    public static void main(String[] args) {


        DefaultEventExecutor eventExecutor = new DefaultEventExecutor();
        DefaultPromise<String> promise = new DefaultPromise<>(eventExecutor);

        promise.addListener(new GenericFutureListener<Future<? super String>>() {
            @Override
            public void operationComplete(Future<? super String> future) throws Exception {
                if(future.isSuccess()){
                    System.out.println("task is success,result="+promise.get());
                }else{
                    System.out.println("task is failure,result="+promise.cause());
                }
            }
        }).addListener(new GenericFutureListener<Future<? super String>>() {
            @Override
            public void operationComplete(Future<? super String> future) throws Exception {
                System.out.println("任务结束，balabala...");
            }
        });

        //具体的任务不一定需要在这个线程池中执行
        eventExecutor.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
//                    promise.setSuccess("123456");
                    promise.setFailure(new RuntimeException("runtime exception"));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        try {
            //阻塞等待promise执行完成
            //sync与await的区别：sync当遇到异常时会抛出，await则不会抛出
//            promise.sync();
            promise.await();
//            Thread.currentThread().join();
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
