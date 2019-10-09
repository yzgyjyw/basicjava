package exchange;

import java.util.concurrent.Exchanger;

public class ExchangeTest2 {

    public static void main(String[] args) throws InterruptedException {
        Exchanger<String> exchanger = new Exchanger<>();


        new Thread(()->{
            String product = "phone";
            System.out.println("I'm saler");
            try {
                System.out.println("sale the "+product+", get the "+exchanger.exchange(product));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(()->{
            String money = "10000$";
            System.out.println("I'm buyer");
            try {
                System.out.println("spend "+money+", get the "+exchanger.exchange(money));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        Thread.sleep(100);
    }

}
