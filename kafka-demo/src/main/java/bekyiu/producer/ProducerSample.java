package bekyiu.producer;

import org.apache.kafka.clients.producer.*;
import org.junit.Before;
import org.junit.Test;

import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @Author: wangyc
 * @Date: 2020/10/18 20:40
 * https://juejin.im/post/6844903700670971911
 */
public class ProducerSample
{
    private static final String TOPIC = "nishino-nanase";
    // 线程安全
    private Producer<String, String> producer;

    @Before
    public void build()
    {
        Properties config = new Properties();
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "47.108.119.93:9092");
        // 是否需要broker应答
        config.put(ProducerConfig.ACKS_CONFIG, "all");
        config.put(ProducerConfig.RETRIES_CONFIG, "0");
        // 发送缓冲区大小
        config.put(ProducerConfig.BUFFER_MEMORY_CONFIG, "33554432");
        // producer会把发往同一分区的多条消息封装进一个batch中，当batch满了后，producer才会把消息发送出去
        config.put(ProducerConfig.BATCH_SIZE_CONFIG, "16384");
        // 延时1ms, 如果batch没满也发送
        config.put(ProducerConfig.LINGER_MS_CONFIG, "1");

        // key, value 序列化
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        // 自定义负载均衡到哪个partition
         config.put(ProducerConfig.PARTITIONER_CLASS_CONFIG, "bekyiu.producer.SamplePartition");

        producer = new KafkaProducer<>(config);
    }

    // 异步发送
    @Test
    public void asyncSend()
    {
        for (int i = 0; i < 10; i++)
        {
            ProducerRecord<String, String> record =
                    new ProducerRecord<>(TOPIC, "async, k" + i, "async, v" + i);
            producer.send(record);
        }
        producer.close();
    }

    // 异步阻塞
    @Test
    public void asyncBlockSend() throws ExecutionException, InterruptedException
    {
        for (int i = 0; i < 10; i++)
        {
            ProducerRecord<String, String> record =
                    new ProducerRecord<>(TOPIC, "block, k" + i, "block, v" + i);
            Future<RecordMetadata> result = producer.send(record);
            RecordMetadata metadata = result.get();
            System.out.println(metadata);
        }
        producer.close();
    }

    // 异步callback
    @Test
    public void callbackSend()
    {
        for (int i = 0; i < 10; i++)
        {
            ProducerRecord<String, String> record =
                    new ProducerRecord<>(TOPIC, "callback, k" + i, "callback, v" + i);
            producer.send(record, new Callback() {
                @Override
                public void onCompletion(RecordMetadata metadata, Exception exception)
                {
                    System.out.println(metadata);
                }
            });
        }
        producer.close();
    }

}
