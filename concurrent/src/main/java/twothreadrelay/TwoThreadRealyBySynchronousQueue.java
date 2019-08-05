package twothreadrelay;

import java.util.concurrent.SynchronousQueue;

public class TwoThreadRealyBySynchronousQueue  {
    public static void main(String[] args) {
        SynchronousQueue<Event> synchronousQueue = new SynchronousQueue<>();
        new ThreadA(synchronousQueue).start();
        new ThreadB(synchronousQueue).start();
    }
}

class ThreadA extends Thread{
    SynchronousQueue<Event> queue;

    public ThreadA(SynchronousQueue<Event> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        while(true){
            try {
                System.out.println("ThreadA先设置event");
                queue.put(new Event(System.currentTimeMillis()));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class ThreadB extends Thread{
    SynchronousQueue<Event> queue;

    public ThreadB(SynchronousQueue<Event> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        while(true){
            try {
                Event event = queue.take();
                System.out.println("ThreadB接着打印event");
                System.out.println(event.getValue());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}


