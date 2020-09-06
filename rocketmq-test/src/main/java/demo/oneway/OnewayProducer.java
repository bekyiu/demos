package demo.oneway;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

/**
 * 单向消息, 不关心发送消息的结果
 */
public class OnewayProducer
{
    public static void main(String[] args) throws Exception
    {
        // 实例化消息生产者Producer
        DefaultMQProducer producer = new DefaultMQProducer("nogizaka46");
        // 设置NameServer的地址
        producer.setNamesrvAddr("39.106.156.11:9876");
        // 启动Producer实例
        producer.start();
        // 创建消息，并指定Topic，Tag和消息体
        Message msg = new Message("TopicTest" /* Topic */,
                "one" /* Tag */,
                ("Hello RocketMQ ").getBytes(RemotingHelper.DEFAULT_CHARSET) /* Message body */
        );
        // 发送单向消息，没有任何返回结果
        producer.sendOneway(msg);

        // 如果不再发送消息，关闭Producer实例。
        producer.shutdown();
    }
}