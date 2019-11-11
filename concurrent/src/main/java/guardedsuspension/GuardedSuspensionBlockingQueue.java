package guardedsuspension;

import com.sun.xml.internal.bind.v2.model.annotation.RuntimeAnnotationReader;

import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Predicate;

public class GuardedSuspensionBlockingQueue {
    public static void main(String[] args) {
        RequestQueue queue = new RequestQueue();
        new Thread(new Server(queue)).start();
        new Thread(new Client(queue)).start();
    }
}


class Message {
    public String id;
    public String content;

    public Message(String id, String content) {
        this.id = id;
        this.content = content;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id='" + id + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}


class GuardedSuspensionRequestQueue<T> {
    private RequestQueue<T> requestQueue;

    private ReentrantLock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    public GuardedSuspensionRequestQueue(RequestQueue requestQueue) {
        this.requestQueue = requestQueue;
    }

    public T getMessage(Predicate<RequestQueue> predicate) {
        try {
            lock.lock();
            while (!predicate.test(requestQueue)) {
                condition.await();
            }
        } catch (Exception e) {

        } finally {
            lock.unlock();
        }
        return requestQueue.get();
    }

    public void put(T obj) {
        try {
            lock.lock();
            requestQueue.put(obj);
            condition.signalAll();
        } catch (Exception e) {

        } finally {
            lock.unlock();
        }
    }
}

class RequestQueue<T> {
    private LinkedList<T> list = new LinkedList<>();

    public T get() {
        return list.removeFirst();
    }

    public void put(T obj) {
        list.addLast(obj);
    }

    public int size() {
        return list.size();
    }
}

class Server implements Runnable {
    private GuardedSuspensionRequestQueue<Message> guardedObject;
    private RequestQueue requestQueue;

    public Server(RequestQueue requestQueue) {
        this.requestQueue = requestQueue;
        this.guardedObject = new GuardedSuspensionRequestQueue<>(requestQueue);
    }

    @Override
    public void run() {
        while (true) {
            Message message = guardedObject.getMessage(requestQueue -> requestQueue.size() > 0);
            System.out.println(message);
        }
    }
}

class Client implements Runnable {
    private GuardedSuspensionRequestQueue<Message> guardedObject;
    private RequestQueue<Message> requestQueue;

    public Client(RequestQueue requestQueue) {
        this.requestQueue = requestQueue;
        this.guardedObject = new GuardedSuspensionRequestQueue<>(requestQueue);
    }

    @Override
    public void run() {
        int count = 0;
        while (true) {
            guardedObject.put(new Message(String.valueOf(++count), String.valueOf(count)));
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}