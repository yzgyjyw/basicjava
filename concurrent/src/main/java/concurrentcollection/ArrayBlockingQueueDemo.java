package concurrentcollection;

import java.util.concurrent.ArrayBlockingQueue;

public class ArrayBlockingQueueDemo {
    public static void main(String[] args) {
        testIsCycle();
    }

    public static void testIsCycle(){
        ArrayBlockingQueue<String> arrayBlockingQueue = new ArrayBlockingQueue<>(2);

        arrayBlockingQueue.add("1");
        arrayBlockingQueue.add("2");
        arrayBlockingQueue.add("3");

        System.out.println(arrayBlockingQueue.poll());
        System.out.println(arrayBlockingQueue.poll());
        System.out.println(arrayBlockingQueue.poll());
    }
}
