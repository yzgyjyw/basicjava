package guardedsuspension;


public class GuardedObjectTest {
    public static void main(String[] args) {
        Message message = new Message("1", "");

        GuardedObject<Message> guardedObject = GuardedObject.create(message.id);

        new Thread(new GuardedObjectTestThread(message)).start();

        Message message1 = guardedObject.get(t -> t != null);

        System.out.println(message1);
    }
}

class Message {
    String id;
    String result;

    public Message(String id, String result) {
        this.id = id;
        this.result = result;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id='" + id + '\'' +
                ", result='" + result + '\'' +
                '}';
    }
}

class GuardedObjectTestThread implements Runnable {

    Message message;

    public GuardedObjectTestThread(Message message) {
        this.message = message;
    }

    @Override
    public void run() {
        //模拟异步耗时操作
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        message.result = "message result";

        GuardedObject.fireEvent(message.id, message);
    }
}