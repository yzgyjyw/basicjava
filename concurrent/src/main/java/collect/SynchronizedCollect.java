package collect;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// 使用synchronized的同步容器，是不合理的，因为在涉及到遍历和一些复合操作的时候，可能会出现一些异常
public class SynchronizedCollect {

    public static void main(String[] args) {

        List<String> list = Collections.synchronizedList(new ArrayList<>());
        for (int i = 0; i < 10; i++) {
            list.add(i + "");
        }
        new Thread(new ThreadUpdateList(list)).start();
        list.remove(8);
        list.remove(7);

    }

}

class ThreadUpdateList implements Runnable {

    private List<String> list;

    public ThreadUpdateList(List<String> list) {
        this.list = list;
    }

    @Override
    public void run() {
        //遍历的时候，如果有别的线程修改了list的大小，那么可能就会抛出IndexOutOfBoundsException
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));
        }

        // 使用迭代器，则有可能会抛出ConcurrentModificationException
        for (String str : list) {
            System.out.println(str);
        }
    }
}
