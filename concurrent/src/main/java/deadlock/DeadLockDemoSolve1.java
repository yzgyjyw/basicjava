package deadlock;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

//解决死锁之循环等待
public class DeadLockDemoSolve1 {

    public static void main(String[] args) throws InterruptedException {

        ExecutorService executorService = Executors.newFixedThreadPool(20);

        UserAccount A = new UserAccount(100000, "A");
        UserAccount B = new UserAccount(100000, "B");

        //正常请求是不会有死锁问题的,因为每一次都是先申请A锁再申请B锁
        /*for (int i = 0; i < 100000; i++) {
            executorService.execute(() -> {
                A.transfer(B, 1);
            });
        }*/


        for (int i = 0; i < 100000; i++) {
            executorService.execute(() -> {
                A.transfer(B, 1);
            });
            executorService.execute(() -> {
                B.transfer(A, 1);
            });
        }


        executorService.shutdown();

        while (!executorService.awaitTermination(1, TimeUnit.MILLISECONDS)) {

        }

        System.out.println(A);
        System.out.println(B);

    }

    static class UserAccount {
        public double money;
        public String name;

        public UserAccount(double money, String name) {
            this.money = money;
            this.name = name;
        }

        //根据name的大小,按照顺序申请锁
        public void transfer(UserAccount account, double price) {

            UserAccount first = this;
            UserAccount second = account;
            if(this.name.compareTo(account.name) > 0){
                first = account;
                second = this;
            }

            synchronized (first) {
                synchronized (second) {
                    this.money -= price;
                    account.money += price;
                }
            }
        }

        @Override
        public String toString() {
            return "UserAccount{" +
                    "money=" + money +
                    ", name='" + name + '\'' +
                    '}';
        }
    }
}

