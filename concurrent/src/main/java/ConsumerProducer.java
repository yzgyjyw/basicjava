import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;

public class ConsumerProducer {


    public static void main(String[] args) {


        List<Integer> list = new ArrayList<>();

        Consumer consumer = new Consumer(list);

        Producer producer = new Producer(list,5);


        new Thread(()->{
            while (true){
                try {
                    Integer consume = consumer.consume();
                    System.out.println(consume);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();


        new Thread(()->{
            int i = 0;
            while (true){
                try {
                    producer.produce(i++);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();


    }




    static class Consumer{
        private List<Integer> list;

        public Consumer(List<Integer> list) {
            this.list = list;
        }

        public Integer consume() throws InterruptedException {

            synchronized (list){
                while (list.isEmpty()){
                    System.out.println("暂无消息可取!");
                    list.wait();
                }

                Integer remove = list.remove(0);

                list.notifyAll();

                return remove;
            }
        }
    }

    static class Producer{
        private List<Integer> list;
        private int maxSize;

        public Producer(List<Integer> list, int maxSize) {
            this.list = list;
            this.maxSize = maxSize;
        }

        public void produce(Integer number) throws InterruptedException {

            synchronized (list){
                while (list.size()>=maxSize){
                    System.out.println("队列已满!"+list.size());
                    list.wait();
                }

                list.add(number);
                list.notifyAll();
            }
        }
    }


}
