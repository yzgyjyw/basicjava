package threadpool;

import org.junit.Test;
import thradpool.AsyncThreadPoolExecutor;

public class AsyncThreadPoolExecutorTest {

    @Test
    public void testAsyncThreadPoolExecutor() throws InterruptedException {
        while (true) {
            AsyncThreadPoolExecutor.execute(() -> {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });

            Thread.sleep(200);
        }
    }


    @Test
    public void testSwapInteger() {
        Integer a = 1;
        Integer b = 2;
        swap(a, b);
        System.out.println(a + "\t" + b);
    }

    public void swap(Integer a, Integer b) {
        Integer c = a;
        a = b;
        b = c;
    }

    @Test
    public void testSwapInt() {
        int a = 1;
        int b = 2;
        swap(a, b);
        System.out.println(a + "\t" + b);
    }

    public void swap(int a, int b) {
        Integer c = a;
        a = b;
        b = c;
    }

    @Test
    public void testSwapString() {
        String a = "1";
        String b = "2";
        swap(a, b);
        System.out.println(a + "\t" + b);
    }

    public void swap(String a, String b) {
        String c = a;
        a = b;
        b = c;
    }


}
