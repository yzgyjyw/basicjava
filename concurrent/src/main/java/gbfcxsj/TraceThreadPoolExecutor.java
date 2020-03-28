package gbfcxsj;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by 13 on 2017/5/5.
 */
public class TraceThreadPoolExecutor extends ThreadPoolExecutor {
    
    public TraceThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }

    public void execute(Runnable task) {
        super.execute(wrap(task, clientTrace(), Thread.currentThread().getName()));
    }

    // 将普通的runnable对象的run方法进行包装，增加一个try catch
    private Runnable wrap(final Runnable task, final Exception clientTrace, String name) {
        return new Runnable() {
            @Override
            public void run() {
                try {
                    task.run();
                } catch (Exception e) {
                    clientTrace.printStackTrace();
                    throw e;
                }
            }
        };
    }

    private Exception clientTrace() {
        return new Exception("Client stack trace");
    }
}