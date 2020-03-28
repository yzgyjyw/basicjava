package com.jyw.lq.cas;

import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.util.stream.IntStream;

public class MyLock {
    static class Node {
        private Thread thread;
        private Node prev;
        private Node next;

        public Node(Thread thread, Node prev) {
            this.prev = prev;
            this.thread = thread;
        }
    }

    private volatile int state;
    private volatile Node head;
    private volatile Node tail;

    private static Unsafe unsafe;
    private static long stateOffset;
    private static long tailOffset;

    static {
        try {
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            unsafe = (Unsafe) field.get(null);
            stateOffset = unsafe.objectFieldOffset(MyLock.class.getDeclaredField("state"));
            tailOffset = unsafe.objectFieldOffset(MyLock.class.getDeclaredField("tail"));
        } catch (Exception e) {
        }
    }

    public MyLock() {
        // 初始化参照AQS的初始化
        head = tail = new Node(null, null);
    }

    private boolean compareAndSetState() {
        return unsafe.compareAndSwapInt(this, stateOffset, 0, 1);
    }

    private boolean compareAndAddNode(Node tail, Node node) {
        return unsafe.compareAndSwapObject(this, tailOffset, tail, node);
    }

    private Node enque() {
        while (true) {
            Node t = tail;
            Node node = new Node(Thread.currentThread(), t);
            if (compareAndAddNode(t, node)) {
                t.next = node;
                return node;
            }
        }
    }

    public void lock() {
        // 如果设置成功则表示成功获取到锁
        if (compareAndSetState()) {
            return;
        }
        // 没有成功获取到锁.将节点入队
        Node node = enque();
        while (node.prev != head || !compareAndSetState()) {
            // 当前node不是head(确保按照入队的顺序获取锁)或者设置state没有成功则阻塞当前线程
            unsafe.park(false, 0L);
        }
        //head永远指向上一个获取锁的Node节点
        head = node;
        // 清除head节点的内的引用,协助GC
        node.thread = null;
        node.prev = null;
        node.next = null;
    }

    public void unLock() {
        // 同一时刻只会有一个线程执行这个方法,故不存在并发
        state = 0;
        Node next = head.next;
        if (next != null) {
            // 唤醒队列中下一个节点的线程
            unsafe.unpark(next.thread);
        }
    }

    static int count = 0;

    public static void main(String[] args) throws InterruptedException {

        MyLock myLock = new MyLock();
        IntStream.range(0, 100000).forEach(i -> new Thread(() -> {
            myLock.lock();
            try {
                count++;
            } finally {
                myLock.unLock();
            }

        }).start());

        Thread.sleep(1000);
        System.out.println(count);
    }
}
