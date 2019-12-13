package threaddemo;

public class ThreadAndInterrupt {

    public static void main(String[] args) throws InterruptedException {

//        interrupted();
        interrupted2();

    }

    // interrupted是Thread类的一个静态方法,这个方法会获取当前线程的interrupt状态,并且清除线程的interrupt状态
    private static void interrupted() throws InterruptedException {
        Thread threadOne = new Thread(() -> {
            for (; ; ) {

            }
        });

        threadOne.start();

        threadOne.interrupt();

        System.out.println(threadOne.isInterrupted());
        // interrupted方法是Thread类的静态方法，这边只是获取main线程的interrupt状态
        System.out.println(threadOne.interrupted());
        System.out.println(Thread.interrupted());
        System.out.println(threadOne.isInterrupted());

        threadOne.join();

        System.out.println("main thread end");
    }


    private static void interrupted2() throws InterruptedException {
        Thread threadOne = new Thread(() -> {
            while (!Thread.interrupted()) {

            }
            //interrupted会清除interrupt状态.因此这便会打印false
            System.out.println("threadOne\t" + Thread.currentThread().isInterrupted());
        });

        threadOne.start();

        threadOne.interrupt();

        threadOne.join();

        System.out.println("main thread end");
    }


}
