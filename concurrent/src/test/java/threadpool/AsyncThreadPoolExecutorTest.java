package threadpool;

import org.junit.Test;
import thradpool.AsyncThreadPoolExecutor;

public class AsyncThreadPoolExecutorTest {

    @Test
    public void testAsyncThreadPoolExecutor() throws InterruptedException {
        while(true){
            AsyncThreadPoolExecutor.execute(()->{
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });

            Thread.sleep(200);
        }
    }

}
