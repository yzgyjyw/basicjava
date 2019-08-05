import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Collections;
import java.util.Properties;

public class NewKafkaConsumer {
    public static void main(String[] args) {
        Properties properties = new Properties();
        KafkaConsumer<String, String> kafkaConsumer = new KafkaConsumer<>(properties);
        kafkaConsumer.subscribe(Collections.singletonList("test01"));

        kafkaConsumer.poll(1000);
    }
}
