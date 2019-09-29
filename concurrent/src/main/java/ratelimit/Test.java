package ratelimit;

import com.google.common.util.concurrent.RateLimiter;

public class Test {
    public static void main(String[] args) {
        testSmoothBursty();
    }

    public static void testSmoothBursty() {
        RateLimiter r = RateLimiter.create(0.5);

        while (true) {
            // 0 ratelimit采用滞后处理的方式应对令牌不足:前一个请求获取令牌等待的时间需要由后一个请求承受
            System.out.println("get 1 tokens: "+ r.acquire(1) + "s");  //0

            System.out.println("get 1 tokens: "+ r.acquire(1) + "s");   //0.5
            System.out.println("get 1 tokens: "+ r.acquire(1) + "s");   //1
            System.out.println("get 1 tokens: "+ r.acquire(1) + "s");
            System.out.println("get 1 tokens: "+ r.acquire(1) + "s");
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
