package ratelimit;

import com.google.common.util.concurrent.RateLimiter;

public class Test {
    public static void main(String[] args) {
        testSmoothBursty();
    }

    public static void testSmoothBursty() {
        RateLimiter r = RateLimiter.create(2);

        while (true) {
            System.out.println("get 1 tokens: "+ r.acquire(1) + "s");
            try {
                Thread.sleep(2000);
            } catch (Exception e) { }

            System.out.println("get 1 tokens: "+ r.acquire(1) + "s");
            System.out.println("get 1 tokens: "+ r.acquire(1) + "s");
            System.out.println("get 1 tokens: "+ r.acquire(1) + "s");
            System.out.println("end");
        }
    }
}
