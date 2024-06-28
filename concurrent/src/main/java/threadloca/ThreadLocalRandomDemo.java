package threadloca;

import java.util.concurrent.ThreadLocalRandom;

public class ThreadLocalRandomDemo {

    public static void main(String[] args) {


        new Thread(() -> {
            ThreadLocalRandom random = ThreadLocalRandom.current();
            System.out.println(Thread.currentThread().getName() + "\t" + random.nextInt());
        }).start();

        new Thread(() -> {
            ThreadLocalRandom random = ThreadLocalRandom.current();
            System.out.println(Thread.currentThread().getName() + "\t" + random.nextInt());
        }).start();

    }

}
