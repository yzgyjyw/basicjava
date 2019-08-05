import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
import org.apache.kafka.common.serialization.IntegerDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class KafkaConsumerSample {
    private static final String zkAddress = "127.0.0.1:2181/kafka";
    public static void main(String[] args) throws InterruptedException {
        for (int j = 0; j < 2; j++) {
            ExecutorService fixedThreadPool = Executors.newFixedThreadPool(5);

            Properties props = new Properties();
            props.put("zookeeper.connect", zkAddress);
            props.put("group.id", "hello-group"+j);
            props.put("enable.auto.commit", "true");
            props.put("auto.commit.interval.ms", "1000");
            props.put("session.timeout.ms", "300000000000");

            Map<String, Integer> topicCountMap = new HashMap<>();
            topicCountMap.put("test01", 2);
            topicCountMap.put("test02", 3);


            ConsumerConnector consumerConnector = Consumer.createJavaConsumerConnector(new ConsumerConfig(props));

            Map<String, List<KafkaStream<byte[], byte[]>>> messageStreams = consumerConnector.createMessageStreams(topicCountMap);

        /*messageStreams.forEach((k, v) -> {
            v.forEach(stream -> {
                fixedThreadPool.execute(() -> {
                    ConsumerIterator<byte[], byte[]> iterator = stream.iterator();
                    while (iterator.hasNext()) {
                        System.out.println(Thread.currentThread().getName() + "\t" + k + "\t" + new String(iterator.next().message()));
                    }
                });
            });
        });*/


            List<Thread> threads = new ArrayList<>();
            messageStreams.forEach((k, v) -> {
                for (int i = 0; i < v.size(); i++) {
                    threads.add(new Handler(v.get(i)));
                }
            });

            threads.forEach(t -> t.start());
        }

//        Thread.sleep(10000);
//        System.out.println("10s后关闭线程1:\t" + threads.get(0).getName());
//        threads.get(0).interrupt();
    }


}

class Handler extends Thread {

    private KafkaStream<byte[], byte[]> kafkaStream;

    public Handler(KafkaStream<byte[], byte[]> kafkaStream) {
        this.kafkaStream = kafkaStream;
    }

    @Override
    public void run() {
        ConsumerIterator<byte[], byte[]> iterator = kafkaStream.iterator();
        while (iterator.hasNext() && !Thread.interrupted()) {
            System.out.println(Thread.currentThread().getName() + "\t" + new String(iterator.next().message()));
        }
    }
}