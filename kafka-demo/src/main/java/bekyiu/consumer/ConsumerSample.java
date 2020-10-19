package bekyiu.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;
import org.junit.Before;
import org.junit.Test;

import java.time.Duration;
import java.util.*;

/**
 * @Author: wangyc
 * @Date: 2020/10/19 19:53
 */
public class ConsumerSample
{
    private static final String TOPIC = "nishino-nanase";
    // 线程不安全
    private KafkaConsumer<String, String> consumer;

    @Before
    public void build()
    {
        Properties props = new Properties();
        props.setProperty("bootstrap.servers", "47.108.119.93:9092");
        // 消费者组, 多个consumer可以属于同一组
        // 单个分区只能由consumer group中的某个consumer消费
        // consumer从partition中消费消息默认是从头开始消费
        // 单个consumer group会消费所有partition中的消息
        props.setProperty("group.id", "test");

        // 每1s自动提交一次offset, 下次消费就不能在消费已提交过的消息
        // 一般都是手动提交 因为处理消息的过程可能会失败或者存在延迟
        // props.setProperty("enable.auto.commit", "true");
        // props.setProperty("auto.commit.interval.ms", "1000");

        props.setProperty("enable.auto.commit", "false");

        props.setProperty("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.setProperty("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        consumer = new KafkaConsumer<>(props);
    }

    // 最简单的消费
    @Test
    public void con()
    {
        // 订阅哪些topic
        consumer.subscribe(Arrays.asList(TOPIC));
        while (true)
        {
            // 每1s拉取一次消息
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(1000));
            System.out.println("拉========================================");
            for (ConsumerRecord<String, String> record : records)
            {
                // 处理...
                System.out.printf("partition = %d, offset = %d, key = %s, value = %s%n",
                        record.partition(), record.offset(), record.key(), record.value());
            }
            // 手动提交offset
            consumer.commitAsync();
        }
    }

    // 按partition分类消费
    @Test
    public void con2()
    {
        consumer.subscribe(Arrays.asList(TOPIC));
        while (true)
        {
            // 拉取所有
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(1000));
            System.out.println("拉========================================");
            // 按partition消费
            for (TopicPartition partition : records.partitions())
            {
                // 每个partition的消息
                List<ConsumerRecord<String, String>> pRecords = records.records(partition);
                for (ConsumerRecord<String, String> record : pRecords)
                {
                    System.out.printf("partition = %d, offset = %d, key = %s, value = %s%n",
                            record.partition(), record.offset(), record.key(), record.value());
                }
                // 单独对某个partition提交它的offset
                long lastOffset = pRecords.get(pRecords.size() - 1).offset();
                Map<TopicPartition, OffsetAndMetadata> map = new HashMap<>();
                // + 1
                map.put(partition, new OffsetAndMetadata(lastOffset + 1));
                consumer.commitSync(map);
                System.out.println("partition: " + partition.partition() + "================================");
            }
        }
    }

    // 仅订阅某个或某几个partition
    @Test
    public void con3()
    {
        TopicPartition p0 = new TopicPartition(TOPIC, 0);
        // 仅订阅了p0
        consumer.assign(Arrays.asList(p0));
        // 指定从哪里开始消费
        // consumer.seek(p0, 100);
        while (true)
        {
            // 拉取所有
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(1000));
            System.out.println("拉========================================");
            // 按partition消费
            for (TopicPartition partition : records.partitions())
            {
                // 每个partition的消息
                List<ConsumerRecord<String, String>> pRecords = records.records(partition);
                for (ConsumerRecord<String, String> record : pRecords)
                {
                    System.out.printf("partition = %d, offset = %d, key = %s, value = %s%n",
                            record.partition(), record.offset(), record.key(), record.value());
                }
                // 单独对某个partition提交它的offset
                long lastOffset = pRecords.get(pRecords.size() - 1).offset();
                Map<TopicPartition, OffsetAndMetadata> map = new HashMap<>();
                map.put(partition, new OffsetAndMetadata(lastOffset + 1));
                consumer.commitSync(map);
                System.out.println("partition: " + partition.partition() + "================================");
            }
        }
    }
}
