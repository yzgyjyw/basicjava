package jvm.reference;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.util.concurrent.Executors;

public class SoftRef {
    static class User {
        public String id;
        public String name;

        public User(String id, String name) {
            this.id = id;
            this.name = name;
        }

        @Override
        public String toString() {
            return "User{" +
                    "id='" + id + '\'' +
                    ", name='" + name + '\'' +
                    '}';
        }
    }

    // 该类继承SoftReference，是为了添加user软引用时指定特殊的id，方便追踪
    static class UserSoftReference extends SoftReference<User> {
        String id;

        public UserSoftReference(User referent, ReferenceQueue<? super User> q) {
            super(referent, q);
            this.id = referent.id;
        }
    }


    public static void main(String[] args) {
        // 创建软引用的时候，可以指定队列，当软引用的对象需要被垃圾回收时，软引用会被先加入到该队列中，此时我们在一个单独的线程中可以跟踪这些即将被gc的软引用
        // 与软引用这一特性一样的，还有弱引用WeakReference，在被gc前也会被加入一个referenceQueue中，方便追踪
        ReferenceQueue<User> referenceQueue = new ReferenceQueue<>();
        Executors.newSingleThreadExecutor().submit(() -> {
            try {
                while (true) {
                    UserSoftReference referenceToDelete = (UserSoftReference) referenceQueue.remove();
                    if (referenceToDelete != null) {
                        System.out.println(String.format("user to delete %s", referenceToDelete.id));
                    }
                }
            } catch (Exception e) {

            }
        });

        User user = new User("1", "1_name");
        SoftReference<User> softUserRef = new UserSoftReference(user, referenceQueue);

        user = null;
        System.out.println(softUserRef.get());

        System.gc();
        System.out.println("After GC");
        System.out.println(softUserRef.get());

        // 分配大对象，此时会gc掉系统中的软引用对象，如果软引用对象被gc后依然不能分配这么多资源，则会OOM
        byte[] b = new byte[1024 * 900 * 7];
        System.gc();
        System.out.println(softUserRef.get());
    }


}
