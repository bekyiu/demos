package demo.order;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.*;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @Author: wangyc
 * @Date: 2020/9/6 14:28
 *
 * 顺序消费
 */
public class OrderConsumer
{
    public static void main(String[] args) throws Exception
    {
        // 实例化消息生产者,指定组名
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("nogizaka46");
        // 指定name server地址信息
        consumer.setNamesrvAddr("39.106.156.11:9876");
        // 订阅Topic
        consumer.subscribe("TopicTest", "top");
        // 负载均衡模式消费
         consumer.setMessageModel(MessageModel.CLUSTERING);
        /*
        * 顺序消息的消费者, 必须是要在consumeMessage()方法返回了之后, 别的线程才能从队列里获取到数据
        * 相当于是给队列加了锁
        * */
        consumer.registerMessageListener(new MessageListenerOrderly()
        {
            @Override
            public ConsumeOrderlyStatus consumeMessage(List<MessageExt> msgs, ConsumeOrderlyContext context)
            {
                try
                {
                    // 此处sleep时, 别的线程也无法从队列中获取消息, 所以保证了顺序性
                    TimeUnit.SECONDS.sleep((long) (Math.random() * 10));
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
                for (MessageExt msg : msgs)
                {
                    System.out.println(Thread.currentThread().getName() + ": "
                            + new String(msg.getBody()) + ", " + msg);
                }
                return ConsumeOrderlyStatus.SUCCESS;
            }
        });
        //启动消息者
        consumer.start();
        System.out.printf("Consumer Started.%n");
    }
}
