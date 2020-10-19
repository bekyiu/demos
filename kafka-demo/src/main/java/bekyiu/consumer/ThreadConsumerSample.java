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
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @Author: wangyc
 * @Date: 2020/10/19 21:20
 */
public class ThreadConsumerSample
{
    private static final String TOPIC = "nishino-nanase";


    // 一个线程是一个consumer, 一个consumer去消费一个partition
    @Test
    public void test() throws InterruptedException
    {
        TopicPartition p0 = new TopicPartition(TOPIC, 0);
        TopicPartition p1 = new TopicPartition(TOPIC, 1);
        KafkaConsumerRunner r0 = new KafkaConsumerRunner(p0);
        KafkaConsumerRunner r1 = new KafkaConsumerRunner(p1);
        new Thread(r0).start();
        new Thread(r1).start();
        Thread.sleep(1000 * 5);
        r0.shutdown();
        r1.shutdown();
    }

    public static class KafkaConsumerRunner implements Runnable
    {
        private AtomicBoolean closed = new AtomicBoolean(false);
        private KafkaConsumer<String, String> consumer;

        public KafkaConsumerRunner(TopicPartition p)
        {
            Properties props = new Properties();
            props.setProperty("bootstrap.servers", "47.108.119.93:9092");
            props.setProperty("group.id", "test");
            props.setProperty("enable.auto.commit", "false");
            props.setProperty("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
            props.setProperty("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
            consumer = new KafkaConsumer<>(props);
            consumer.assign(Arrays.asList(p));
        }

        @Override
        public void run()
        {
            while (!closed.get())
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
                    System.out.println("partition: " + partition.partition() + "==========================");
                }
            }
            consumer.close();
        }

        public void shutdown()
        {
            closed.set(true);
//            consumer.wakeup();
        }
    }
}
