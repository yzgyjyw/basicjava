package threaddemo;

public class ThisAndCurrentThread {
    public static void main(String[] args) throws InterruptedException {
        CountOperate countOperate = new CountOperate();
        Thread thread = new Thread(countOperate);

        thread.start();

        Thread.sleep(1000);
    }
}

class CountOperate extends Thread {

    public CountOperate() {
        System.out.println("construct begin");
        System.out.println("Thread.currentThread().isAlive()\t" + Thread.currentThread().isAlive());
        System.out.println("this.getName()\t" + this.getName());
        System.out.println("Thread.currentThread().isAlive()\t" + Thread.currentThread().isAlive());
        System.out.println("this.getName()\t" + this.getName());
        System.out.println("construct end");
    }

    @Override
    public void run() {
        System.out.println("run begin");
        System.out.println("Thread.currentThread().isAlive()\t" + Thread.currentThread().isAlive());
        System.out.println("this.getName()\t" + this.getName());
        System.out.println("Thread.currentThread().isAlive()\t" + Thread.currentThread().isAlive());
        System.out.println("run end");
    }
}