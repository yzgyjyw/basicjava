import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.internals.AbstractPartitionAssignor;
import org.apache.kafka.common.TopicPartition;

import java.util.*;

public class NewKafkaConsumerPartitionAssign {
    public static void main(String[] args) {
        Properties properties = new Properties();
        properties.put("bootstrap.servers", "localhost:9092");
        properties.put("group.id", "newgroup01");
        properties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put(ConsumerConfig.PARTITION_ASSIGNMENT_STRATEGY_CONFIG,RandomAssign.class.getName());
        KafkaConsumer<String, String> kafkaConsumer = new KafkaConsumer<>(properties);
        List<String> topicList = Collections.singletonList("test01");
        kafkaConsumer.subscribe(topicList);
    }
}

class RandomAssign extends AbstractPartitionAssignor{

    @Override
    public Map<String, List<TopicPartition>> assign(Map<String, Integer> partitionsPerTopic, Map<String, List<String>> subscriptions) {
        // consumerId --> 该consumerId消费的所有TopicPartition
        Map<String, List<TopicPartition>> assignment =  new HashMap<>();
        subscriptions.forEach((k,v)->assignment.put(k,new ArrayList<>()));

        for(Map.Entry<String,List<String>> topicsConsumer:consumersForTopic(subscriptions).entrySet()){
            String topic = topicsConsumer.getKey();
            List<String> consumers = topicsConsumer.getValue();
            int partitionNum = partitionsPerTopic.get(topic);
            // 获取当前topic下的所有TopicPartition对象
            List<TopicPartition> topicPartitions = AbstractPartitionAssignor.partitions(topic, partitionNum);
            for(TopicPartition topicPartition:topicPartitions){
                int rand  = new Random().nextInt(consumers.size());
                assignment.get(consumers.get(rand)).add(topicPartition);
            }
        }
        return assignment;
    }

    @Override
    public String name() {
        return "random";
    }

    /**
     * 获取topic下的所有消费者
     * @param consumerMetaData conusmerId-->List[topic1,topic2...]
     * @return
     */
    public Map<String,List<String>> consumersForTopic(Map<String,List<String>> consumerMetaData){
        Map<String,List<String>> res = new HashMap<>();
        for(Map.Entry<String,List<String>> stringSubscriptionEntry:consumerMetaData.entrySet()){
            String connsumerId = stringSubscriptionEntry.getKey();
            for(String topic:stringSubscriptionEntry.getValue()){
                List<String> topicConsumers = res.putIfAbsent(topic, new ArrayList<>());
                if(topicConsumers == null){
                    topicConsumers = res.get(topic);
                }
                topicConsumers.add(connsumerId);
            }
        }
        return res;
    }
}
