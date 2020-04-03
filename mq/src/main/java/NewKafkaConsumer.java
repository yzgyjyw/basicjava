import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.errors.WakeupException;

import java.util.*;

public class NewKafkaConsumer {
    public static void main(String[] args) {
        Properties properties = new Properties();
        properties.put("bootstrap.servers", "localhost:9092");
        properties.put("group.id", "newgroup01");
        properties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        KafkaConsumer<String, String> kafkaConsumer = new KafkaConsumer<>(properties);
        List<String> topicList = Collections.singletonList("test01");
        kafkaConsumer.subscribe(topicList);
        try {
            while (true) {


                List<ConsumerRecord<String, String>> buffers = new ArrayList<>();
                ConsumerRecords<String, String> consumerRecords = kafkaConsumer.poll(1000);
                // 以topic的维度进行消息处理,需要注意的是ConsumerRecords没有提供topics()方法用来获取consumerRecords中的所有消息的topic
                // 因此只能使用subscribe指定的topicList作为被遍历的topic列表
                for (String topic : topicList) {
                    consumerRecords.records(topic).forEach(record -> {
                        System.out.println(record.key() + "\t" + record.value() + "\t" + record.offset() + "\n");

                    });
                }

                // 获取consuemrRecords中消息的TopicPartition列表
                Set<TopicPartition> partitions = consumerRecords.partitions();
                // 以TopicPartition的维度处理消息
                for (TopicPartition topicPartition : partitions) {
                    List<ConsumerRecord<String, String>> records = consumerRecords.records(topicPartition);
                    records.forEach(record -> {
                        // handle messgae
                    });
                    ConsumerRecord<String, String> lastRecord = records.get(records.size() - 1);
                    kafkaConsumer.commitSync(Collections.singletonMap(topicPartition, new OffsetAndMetadata(lastRecord.offset() + 1)));
                }


                consumerRecords.forEach(record -> {
                    System.out.println(record.key() + "\t" + record.value() + "\t" + record.offset() + "\n");
                    buffers.add(record);
                    if (buffers.size() > 200) {
                        // handle 200 messages
                        kafkaConsumer.commitSync();
                    }
                });

                kafkaConsumer.commitAsync();

                consumerRecords.forEach(record -> {
                    // handle message
                    kafkaConsumer.commitSync(Collections.singletonMap(new TopicPartition(record.topic(), record.partition()), new OffsetAndMetadata(record.offset() + 1)));

                });
            }
        } finally {
            kafkaConsumer.commitAsync();
            kafkaConsumer.close();
        }
    }

    // 一个消费者示例代码
    /*public void example() {
        Properties properties = new Properties();
        properties.put("...", "...");
        KafkaConsumer<String, String> kafkaConsumer = new KafkaConsumer<>(properties);
        List<String> topicList = Collections.singletonList("test01");
        kafkaConsumer.subscribe(topicList);
        try {
            while (isRunning) {
                // kafkaConsumer.poll();
                // process the record;
                // commit offset;
            }
        } catch (WakeupException wakeupException) {
            //ignore   wakeupException
        } catch (Exception ex) {

        } finally {
            //maybe commit offset;
            kafkaConsumer.close();
        }


    }*/

    /*public void seek(){
        Properties properties = new Properties();
        properties.put("...", "...");
        KafkaConsumer<String, String> kafkaConsumer = new KafkaConsumer<>(properties);
        List<String> topicList = Collections.singletonList("test01");
        kafkaConsumer.subscribe(topicList);

        kafkaConsumer.poll(10000);

        Set<TopicPartition> assignment = new HashSet<>();
        while(assignment.size() <= 0 ){
            kafkaConsumer.poll(100);
            assignment = kafkaConsumer.assignment();
        }

        Set<TopicPartition> assignment = kafkaConsumer.assignment();
        assignment.forEach(topicPartition->kafkaConsumer.seek(topicPartition,10));
        while(true){
            ConsumerRecords<String, String> consumerRecords = kafkaConsumer.poll(10000);
            //handle records
        }
    }*/

    public void end(){
        Properties properties = new Properties();
        properties.put("...", "...");
        KafkaConsumer<String, String> kafkaConsumer = new KafkaConsumer<>(properties);
        List<String> topicList = Collections.singletonList("test01");
        kafkaConsumer.subscribe(topicList);



    }
}
