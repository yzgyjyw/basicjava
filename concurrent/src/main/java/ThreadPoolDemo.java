import java.util.concurrent.Executors;

public class ThreadPoolDemo {

    public static void main(String[] args) {
        Executors.newFixedThreadPool(1).submit(()->{
            System.out.println(1);
        });
    }

}
