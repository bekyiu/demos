package demo.order;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.remoting.common.RemotingHelper;

import java.util.List;

/**
 * @Author: wangyc
 * @Date: 2020/9/6 15:08
 *
 * 顺序发送
 * 发送到指定队列中
 */
public class OrderProducer
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
            // 发送消息到一个Broker的0号队列
            SendResult sendResult = producer.send(msg, new MessageQueueSelector()
            {
                /**
                 * @param mqs 所有队列
                 * @param msg 要发送的消息, 外面穿入
                 * @param arg 发送的到哪个队列，外面传入
                 * @return 要发送到的队列
                 */
                @Override
                public MessageQueue select(List<MessageQueue> mqs, Message msg, Object arg)
                {
                    return mqs.get((int) arg);
                }
            }, 0);
            // 通过sendResult返回消息是否成功送达
            System.out.println(sendResult);
        }
        // 如果不再发送消息，关闭Producer实例。
        producer.shutdown();
    }
}
