package deadlock;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class DeadLockDemoThreadUnsafe {

    public static void main(String[] args) throws InterruptedException {

        ExecutorService executorService = Executors.newFixedThreadPool(20);

        UserAccount A = new UserAccount(100000,"A");
        UserAccount B = new UserAccount(100000,"B");

        for (int i = 0; i < 100000; i++) {
            executorService.execute(()->{
                A.transfer(B,1);
            });
        }

        executorService.shutdown();

        while (!executorService.awaitTermination(1, TimeUnit.MILLISECONDS)){

        }

        System.out.println(A);
        System.out.println(B);

    }

}

class UserAccount{
    public double money;
    public String name;

    public UserAccount(double money, String name) {
        this.money = money;
        this.name = name;
    }

    public void transfer(UserAccount account, double price){
        this.money -= price;
        account.money += price;
    }

    @Override
    public String toString() {
        return "UserAccount{" +
                "money=" + money +
                ", name='" + name + '\'' +
                '}';
    }
}