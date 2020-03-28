package guardedsuspension;


import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GuardedObjectTest {
    public static void main(String[] args) {

        /*ExecutorService executorService = Executors.newFixedThreadPool(100);
        for (int i = 0; i < 100; i++) {
            executorService.execute(() -> {
                Request request = new Request(String.valueOf(System.currentTimeMillis()), null);
                GuardedObject<Response> guardedObject = GuardedObject.create(request.id);
                new Thread(new GuardedObjectTestThread(request)).start();
                Response response = guardedObject.get(t -> t != null);
                System.out.println(response);
            });
        }*/

        System.out.println(Math.abs("41148206316".hashCode() % 10000) / 10000.0);
    }
}

class Request {
    String id;
    String content;

    public Request(String id, String content) {
        this.id = id;
        this.content = content;
    }

    @Override
    public String toString() {
        return "Request{" +
                "id='" + id + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}

class Response {
    String id;
    String result;

    public Response(String id, String result) {
        this.id = id;
        this.result = result;
    }

    @Override
    public String toString() {
        return "Response{" +
                "id='" + id + '\'' +
                ", result='" + result + '\'' +
                '}';
    }
}

class GuardedObjectTestThread implements Runnable {

    Request request;

    public GuardedObjectTestThread(Request request) {
        this.request = request;
    }

    @Override
    public void run() {
        //模拟异步耗时操作
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Response response = new Response(request.id, "result:" + request.content);

        //这一步
        GuardedObject.fireEvent(request.id, response);
    }
}