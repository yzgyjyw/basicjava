package jvm;

import org.openjdk.jol.info.ClassLayout;

public class SynchronizedMarkWord {

    public static void main(String[] args) throws InterruptedException {

        Thread.sleep(5000);

        Object object = new Object();

        new Thread(()->{

            System.out.println(ClassLayout.parseInstance(object).toPrintable());

            synchronized (object){
                System.out.println(ClassLayout.parseInstance(object).toPrintable());
            }

            System.out.println(ClassLayout.parseInstance(object).toPrintable());



        }).start();

        Thread.sleep(10000);

        new Thread(()->{

            System.out.println(ClassLayout.parseInstance(object).toPrintable());
            synchronized (object){
                System.out.println(ClassLayout.parseInstance(object).toPrintable());
            }
            System.out.println(ClassLayout.parseInstance(object).toPrintable());

        }).start();


        Thread.sleep(10000);
        new Thread(()->{

            System.out.println(ClassLayout.parseInstance(object).toPrintable());
            synchronized (object){
                System.out.println(ClassLayout.parseInstance(object).toPrintable());
            }
            System.out.println(ClassLayout.parseInstance(object).toPrintable());

        }).start();

    }

}
