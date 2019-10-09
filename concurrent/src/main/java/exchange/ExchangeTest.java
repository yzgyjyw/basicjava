package exchange;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Exchanger;

public class ExchangeTest {
    public static void main(String[] args) {
        Exchanger<List<Integer>> exchanger = new Exchanger<>();
        new Producer(exchanger).start();
        new Consumer(exchanger).start();

    }
}

class Producer extends Thread {

    List<Integer> list = new ArrayList<>();
    Exchanger<List<Integer>> exchanger = null;

    public Producer(Exchanger<List<Integer>> exchanger) {
        super();
        this.exchanger = exchanger;
    }

    @Override
    public void run() {
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            list.clear();
            list.add(random.nextInt(10000));
            list.add(random.nextInt(10000));
            list.add(random.nextInt(10000));
            list.add(random.nextInt(10000));
            list.add(random.nextInt(10000));

            try {
                list=exchanger.exchange(list);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class Consumer extends Thread{
    List<Integer> list = new ArrayList<>();
    Exchanger<List<Integer>> exchanger = null;
    public Consumer(Exchanger<List<Integer>> exchanger) {
        super();
        this.exchanger = exchanger;
    }
    @Override
    public void run() {
        for(int i=0; i<10; i++) {
            try {
                //线程会阻塞在exchahnge方法上,直到另一个线程也调用了同一个Exchange对象的exchange方法
                list = exchanger.exchange(list);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            System.out.print(list.get(0)+", ");
            System.out.print(list.get(1)+", ");
            System.out.print(list.get(2)+", ");
            System.out.print(list.get(3)+", ");
            System.out.println(list.get(4)+", ");
        }
    }
}
