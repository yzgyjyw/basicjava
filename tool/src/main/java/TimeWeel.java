import delayedtask.RingBufferWheel;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class TimeWeel {

    static volatile long end = 0;

    private List<List<Task>> ring;

    private int size;


    private ScheduledExecutorService scheduleExecutorService = Executors.newSingleThreadScheduledExecutor();

    // 记录当前已经执行的次数
    private AtomicInteger atomicInteger = new AtomicInteger();

    private int index = 0;


    private TimeWeel(int size) {
        this.size = size;

        // 初始化环形数组
        ring = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            ring.add(new ArrayList<>());
        }

        scheduleExecutorService.scheduleAtFixedRate(() -> {
            // 获取槽号为index并且轮数为0的所有的任务
            List<Task> tasks = ring.get(index);
            synchronized (tasks) {
                Iterator<Task> iterator = tasks.iterator();

                while (iterator.hasNext()) {
                    Task next = iterator.next();
                    if (next.cycleNum-- == 0) {
                        System.out.println(System.currentTimeMillis() + "\t" + next + "\t" + (System.currentTimeMillis() - next.initialTime));
                        iterator.remove();
                    }
                }
            }

            if (++index > size - 1) {
                index = 0;
            }

            //Total tick number of records
            atomicInteger.incrementAndGet();

        }, 0, 1000, TimeUnit.MILLISECONDS);
    }


    public void add(Task task) {
        int cycleNum = task.delay / size;
        int index = (atomicInteger.get() + task.delay) & (size - 1);

        task.cycleNum = cycleNum;

        List<Task> targetList = ring.get(index);

        synchronized (targetList) {
            targetList.add(task);
        }
    }


    static class Task {
        // 以毫秒为单位
        int delay;

        long initialTime;

        // 轮训的次数
        int cycleNum;

        String name;

        public Task(int delay, String name) {
            this.delay = delay;
            this.name = name;
            this.initialTime = System.currentTimeMillis();
        }

        @Override
        public String toString() {
            return "Task{" +
                    "delay=" + delay +
                    ", initialTime=" + initialTime +
                    ", cycleNum=" + cycleNum +
                    ", name='" + name + '\'' +
                    '}';
        }
    }


    public static void main(String[] args) {


        TimeWeel timeWeel = new TimeWeel(128);

        ExecutorService executorService = Executors.newFixedThreadPool(10);

        long start = System.currentTimeMillis();


        for (int i = 0; i < 800000; i++) {
            executorService.submit(() -> {

                int delay = 1 + new Random().nextInt(20);


                long ts = System.currentTimeMillis();

                timeWeel.add(new Task(delay, String.valueOf(ts)));

                end = ts;
            });

        }


        System.out.println("submit success\t" + (end - start));


    }

}



