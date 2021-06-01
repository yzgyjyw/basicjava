import java.util.concurrent.CountDownLatch;

public class MultiThreadSequencePrint {

    public static void main(String[] args) throws InterruptedException {

        Object o = new Object();

        CountDownLatch countDownLatch = new CountDownLatch(1);


        Thread threadA = new Thread(() -> {
            for(int i=0;i<10;i+=2){
                synchronized (o){
                    System.out.println(i);
//                    countDownLatch.countDown();
                    o.notify();
                    try {
                        o.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

            System.out.println("线程A打印完");
        });


        Thread threadB = new Thread(() -> {
        /*    try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/
            for(int i=1;i<10;i+=2){
                synchronized (o){
                    System.out.println(i);
                    o.notify();
                    try {
                        if(i<9) {
                            o.wait();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            System.out.println("线程B打印完");
        });


        threadB.start();
        Thread.sleep(10000);
        threadA.start();
    }

}
