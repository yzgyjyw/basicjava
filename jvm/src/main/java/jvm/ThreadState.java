package jvm;

public class ThreadState {


    public static void main(String[] args) throws InterruptedException {

        Object obj = new Object();

        Thread thread = new Thread(()->{
            synchronized (obj){
                try {
                    obj.wait(1000000000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });


        thread.start();

        Thread.sleep(20000);

        obj.notify();

        thread.join();
    }

}
