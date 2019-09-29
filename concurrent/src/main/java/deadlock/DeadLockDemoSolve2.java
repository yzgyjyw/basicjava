package deadlock;

import scala.App;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

//解决死锁之破坏不可剥夺条件
public class DeadLockDemoSolve2 {

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

    static class Apply {
        private static final List<UserAccount> locks = new ArrayList<>();

        public static Apply instance = new Apply();

        public synchronized boolean lock(UserAccount A, UserAccount B) {
            if (locks.contains(A) || locks.contains(B)) {
                return false;
            }

            locks.add(A);
            locks.add(B);
            return true;
        }

        //必须也是一个互斥的方法,不然就会导致之前的add后,又被清除造成线程安全问题
        public synchronized void free(UserAccount A, UserAccount B) {
            locks.remove(A);
            locks.remove(B);
        }

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

            while(! Apply.instance.lock(account,this)){

            }
            this.money -= price;
            account.money += price;

            Apply.instance.free(account,this);
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

