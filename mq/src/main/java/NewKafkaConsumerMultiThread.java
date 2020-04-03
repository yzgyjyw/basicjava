import kafka.common.OffsetsLoadInProgressException;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;

import java.util.*;
import java.util.concurrent.*;

public class NewKafkaConsumerMultiThread {
    Map<TopicPartition,OffsetAndMetadata> offsets = new HashMap<>();
    Properties properties = new Properties();

    public NewKafkaConsumerMultiThread(Map<TopicPartition, OffsetAndMetadata> offsets) {
        properties.put("bootstrap.servers", "localhost:9092");
        properties.put("group.id", "newgroup01");
        properties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
    }

    public void conusme(){

        KafkaConsumer<String, String> kafkaConsumer = new KafkaConsumer<>(properties);
        List<String> topicList = Collections.singletonList("test01");
        kafkaConsumer.subscribe(topicList);

        ThreadPoolExecutor executor = new ThreadPoolExecutor(0, Runtime.getRuntime().availableProcessors() * 2,
                0, TimeUnit.SECONDS, new LinkedBlockingQueue<>(1000), new ThreadPoolExecutor.CallerRunsPolicy());
        try {
            while (true) {

                ConsumerRecords<String, String> consumerRecords = kafkaConsumer.poll(1000);
                consumerRecords.forEach(record -> {
                    executor.submit(new RecordHandler(record,offsets));
                });

                synchronized (offsets){
                    // 注意,在这边提交会影响
                    kafkaConsumer.commitSync(offsets);
                    offsets.clear();
                }
            }
        } catch (Exception ex) {
            // handle ex
        } finally {
            kafkaConsumer.close();
        }
    }
}

class RecordHandler implements Runnable {
    private ConsumerRecord consumerRecord;
    private final Map<TopicPartition,OffsetAndMetadata> offsets;

    public RecordHandler(ConsumerRecord consumerRecord,Map<TopicPartition,OffsetAndMetadata> offsets) {
        this.consumerRecord = consumerRecord;
        this.offsets = offsets;
    }

    @Override
    public void run() {
        // handle records
        updateLocalOffsets();
    }

    // 采用这种方式提交位移，有可能会造成消息丢失的情况：第一个handler处理了offsets为2的消息，但是处理失败了，第2个handler处理了offset为3的消息并提交位移成功
    // 那么offset=2的消息就永远不会再有机会被消费了

    public void updateLocalOffsets(){
        synchronized (offsets){
            TopicPartition topicPartition = new TopicPartition(consumerRecord.topic(), consumerRecord.partition());

            if(!offsets.containsKey(topicPartition)){
                offsets.put(topicPartition,new OffsetAndMetadata(consumerRecord.offset()+1));
            }else{
                long position = offsets.get(topicPartition).offset();
                if(position < consumerRecord.offset()+1){
                    offsets.put(topicPartition,new OffsetAndMetadata(consumerRecord.offset()+1));
                }
            }
        }
    }
}
