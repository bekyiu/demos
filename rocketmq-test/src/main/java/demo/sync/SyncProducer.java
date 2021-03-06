package demo.sync;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

import java.util.concurrent.TimeUnit;

/**
 * 同步发送消息
 */
public class SyncProducer
{
    public static String[] members = {"白", "桥", "七", "花", "鸟"};

    public static void main(String[] args) throws Exception
    {
        // 实例化消息生产者Producer
        DefaultMQProducer producer = new DefaultMQProducer("nogizaka46");
        // 设置NameServer的地址
        producer.setNamesrvAddr("39.106.156.11:9876");
        // 启动Producer实例
        producer.start();
        for (int i = 0; i < 5; i++)
        {
            // 创建消息，并指定Topic，Tag和消息体
            Message msg = new Message("TopicTest" /* Topic */,
                    "top" /* Tag */,
                    members[i].getBytes(RemotingHelper.DEFAULT_CHARSET) /* Message body */
            );
            // 发送消息到一个Broker
            SendResult sendResult = producer.send(msg);
            // 通过sendResult返回消息是否成功送达
            System.out.println(sendResult);
            TimeUnit.SECONDS.sleep(1);
        }
        // 如果不再发送消息，关闭Producer实例。
        producer.shutdown();
    }
}