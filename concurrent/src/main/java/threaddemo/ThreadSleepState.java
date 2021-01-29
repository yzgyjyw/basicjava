package threaddemo;

public class ThreadSleepState {


    // jstack查看线程处于sleep时的线程状态
    public static void main(String[] args) {

        // sleep后,该线程的状态为TIME_WAITING
        Thread thread = new Thread("thread_sleep_state"){
            @Override
            public void run() {
                try {
                    Thread.sleep(100000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        thread.start();

    }

}
