package delayedtask;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TestRingBufferWheel {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        RingBufferWheel ringBufferWheel = new RingBufferWheel(executorService, 64);

        ringBufferWheel.addTask(new RingBufferWheel.Task() {
            @Override
            public void run() {
                System.out.println(System.currentTimeMillis());
            }
        });

        ringBufferWheel.addTask(new RingBufferWheel.Task() {
            @Override
            public void run() {
                System.out.println(System.currentTimeMillis());
            }

            @Override
            public int getKey() {
                return 64;
            }
        });

        ringBufferWheel.start();

        ringBufferWheel.stop(false);
    }
}
