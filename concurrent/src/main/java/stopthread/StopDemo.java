package stopthread;

import java.sql.SQLOutput;

/**
 * 使用两阶段模式优雅的终止一个线程
 */
public class StopDemo {

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new StopRunnable());
        thread.start();
        Thread.sleep(2000);
        // 发出中断命令
        thread.interrupt();
    }

}

class StopRunnable implements Runnable {

    @Override
    public void run() {
        // 响应中断命令
        while (!Thread.currentThread().isInterrupted()) {
            //DO TASK
            try {
                Thread.sleep(900);
            } catch (InterruptedException e) {
                // 处理中断时,会撤销线程的状态. 为了线程能正常退出, 需要手动重置线程的状态
                // 因此一般情况下,我们不应该使用interrupt来终止一个线程的执行.因为你不知道使用的第三方jar包中有没有正确的处理线程中断的异常(重新标志线程的状态为interrupt)
                // 没有正确处理,可能会导致线程不能退出
                Thread.currentThread().interrupt();
            }

            System.out.println(Thread.currentThread().getName() + "\t" + System.currentTimeMillis());
        }
    }
}