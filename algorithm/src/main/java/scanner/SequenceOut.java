package scanner;

import java.util.concurrent.atomic.AtomicInteger;

public class SequenceOut {

    static Object o = new Object();
    static AtomicInteger count = new AtomicInteger(0);
    public static void main(String[] args) {

        new Thread(()->{
            while (true){
                if(count.get()%4==0){
                    synchronized (o){
                        count.incrementAndGet();
                        System.out.println("A");
                        o.notify();
                        try {
                            o.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }).start();


        new Thread(()->{
            while (true){
                if(count.get()%4==1){
                    synchronized (o){
                        count.incrementAndGet();
                        System.out.println("B");
                        o.notifyAll();
                        try {
                            o.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }).start();


        new Thread(()->{
            while (true){
                if(count.get()%4==2){
                    synchronized (o){
                        count.incrementAndGet();
                        System.out.println("C");
                        o.notifyAll();
                        try {
                            o.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }).start();


        new Thread(()->{
            while (true){
                if(count.get()%4==3){
                    synchronized (o){
                        count.incrementAndGet();
                        System.out.println("D");
                        o.notifyAll();
                        try {
                            o.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }).start();
    }

}
