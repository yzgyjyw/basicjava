import org.apache.kafka.clients.producer.*;

import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicInteger;

public class KafkaProducerSample {
    private final static String hostAndPort = "127.0.0.1:9092";

    public static void main(String[] args) throws InterruptedException {


        Properties properties = new Properties();

        properties.put("bootstrap.servers", hostAndPort);

        properties.put("metadata.broker.list", hostAndPort);

        /**
         * "key.serializer" 的类型根据ProducerRecord<Integer,String>中的类型来确定，
         * Integer对应的为IntegerSerializer,String对应的为StringSerializer
         * key.serializer和value.serializer根据定义的ProducerRecord类型来对应
         */
        properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put(ProducerConfig.INTERCEPTOR_CLASSES_CONFIG,MyInterceptor.class.getName());

        KafkaProducer<String, String> kafkaProducer = new KafkaProducer(properties);
        /**
         * KeyedMessage中"test-topic"为topic的名字，"test-message"为消息内容
         * 6为对应的key值
         * "hello"为对应的value值
         */
        int count = 0;
        while (true) {
            ProducerRecord<String, String> producerRecord1 = new ProducerRecord<>("test01", String.valueOf(count), "hello\t" + count);
            ProducerRecord<String, String> producerRecord2 = new ProducerRecord<>("test02", String.valueOf(count), "hello2\t" + count);
            try {
                RecordMetadata recordMetadata = kafkaProducer.send(producerRecord1).get();
            } catch (ExecutionException e) {
                //TODO
            }

            kafkaProducer.send(producerRecord2, new Callback() {
                @Override
                public void onCompletion(RecordMetadata metadata, Exception exception) {
                    //TODO
                }
            });
            System.out.println("send " + count++);
            Thread.sleep(5000);
        }

//        kafkaProducer.close();

//        System.out.println("product end");
    }
}

class MyInterceptor implements ProducerInterceptor {

    private static AtomicInteger totalMessage;

    @Override
    public ProducerRecord onSend(ProducerRecord record) {
        return new ProducerRecord(record.topic(), record.partition(), record.timestamp(), record.key(), record.value() + "--inteceptor1");

    }

    @Override
    public void onAcknowledgement(RecordMetadata metadata, Exception exception) {

    }

    @Override
    public void close() {

    }

    @Override
    public void configure(Map<String, ?> configs) {

    }
}
