package demo.consumer;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @Author: wangyc
 * @Date: 2020/9/6 14:28
 */
public class Consumer
{
    public static void main(String[] args) throws Exception
    {
        // 实例化消息生产者,指定组名
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("nogizaka46");
        // 指定name server地址信息
        consumer.setNamesrvAddr("39.106.156.11:9876");
        // 订阅Topic
        consumer.subscribe("TopicTest", "top || one");
        // 负载均衡模式消费
         consumer.setMessageModel(MessageModel.CLUSTERING);
        // 注册回调函数，处理消息
        consumer.registerMessageListener(new MessageListenerConcurrently()
        {

            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs,
                                                            ConsumeConcurrentlyContext context)
            {
                try
                {
                    /*
                     * 此处sleep时, 别的线程也可以从队列获取消息并执行, 所以无法保证顺序性
                     */
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
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        //启动消息者
        consumer.start();
        System.out.printf("Consumer Started.%n");
    }
}
