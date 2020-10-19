package bekyiu.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.junit.Test;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @Author: wangyc
 * @Date: 2020/10/19 21:45
 */
public class ExecutorSample
{
    // 一个consumer去拉取所有的partition, 只不过处理是不同的线程在处理
    @Test
    public void test() throws InterruptedException
    {
        ConsumerExecutor executor = new ConsumerExecutor("nishino-nanase", 3);
        executor.execute();
        TimeUnit.SECONDS.sleep(5);
    }

    public static class ConsumerExecutor
    {
        private KafkaConsumer<String, String> consumer;
        private ExecutorService executors;

        public ConsumerExecutor(String topic, int workerNum)
        {
            Properties props = new Properties();
            props.setProperty("bootstrap.servers", "47.108.119.93:9092");
            props.setProperty("group.id", "test");
            props.setProperty("enable.auto.commit", "false");
            props.setProperty("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
            props.setProperty("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
            consumer = new KafkaConsumer<>(props);
            consumer.subscribe(Arrays.asList(topic));
            executors = Executors.newFixedThreadPool(workerNum);
        }

        public void execute()
        {
            while (true)
            {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(5000));
                for (ConsumerRecord<String, String> r : records)
                {
                    executors.submit(new ConsumerRecordWorker(r));
                }
                // 可能有问题 先这样写
                // consumer.commitAsync();
            }
        }
    }

    public static class ConsumerRecordWorker implements Runnable
    {
        private ConsumerRecord<String, String> record;

        public ConsumerRecordWorker(ConsumerRecord<String, String> record)
        {
            this.record = record;
        }

        @Override
        public void run()
        {
            System.out.printf("Thread: %s, partition = %d, offset = %d, key = %s, value = %s%n",
                    Thread.currentThread().getName(), record.partition(),
                    record.offset(), record.key(), record.value());
        }
    }
}
