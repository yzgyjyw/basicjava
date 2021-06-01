package singleton;

import java.util.concurrent.atomic.AtomicLong;

public class EnumSingleton {

    public static void main(String[] args) {
        IdGenerator.INSTANCE.getId();
    }

}

enum IdGenerator {
    INSTANCE;
    private AtomicLong id = new AtomicLong(0);

    public long getId() {
        return id.addAndGet(1);
    }
}