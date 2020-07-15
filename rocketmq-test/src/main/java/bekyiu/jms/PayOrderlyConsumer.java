package bekyiu.jms;

import lombok.Getter;
import lombok.Setter;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.*;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @Author: wangyc
 * @Date: 2020/7/14 20:26
 */
@Component
@Setter
@Getter
public class PayOrderlyConsumer
{
    private DefaultMQPushConsumer consumer;
    private String consumerGroup = "pay_c_o_group";

    public PayOrderlyConsumer()
    {
        try
        {
            consumer = new DefaultMQPushConsumer(consumerGroup);
            consumer.setNamesrvAddr(JmsConfig.NAME_SERVER);
            // 先进先出
            consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
            // 订阅哪个主题的哪个标签
            consumer.subscribe(JmsConfig.ORDER_TOPIC, "*");
            // 设置监听器
            // 从队列中取出消息后开线程执行，从同一个队列取出来的3条消息的执行先后顺序就不确定了
            consumer.registerMessageListener(new MessageListenerConcurrently()
            {
                @Override
                public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context)
                {
                    try
                    {
                        TimeUnit.SECONDS.sleep((long) (Math.random() * 10));
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                    MessageExt msg = msgs.get(0);
                    String body = new String(msg.getBody());
                    String topic = msg.getTopic();
                    String tags = msg.getTags();
                    String msgId = msg.getMsgId();
                    int queueId = msg.getQueueId();
                    System.out.println(String.format("%s, body: %s, topic: %s, tags: %s, msgId: %s, queueId: %d",
                            Thread.currentThread().getName(), body, topic, tags, msgId, queueId));
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                }
            });
            consumer.start();
        }
        catch (MQClientException e)
        {
            e.printStackTrace();
        }
    }
//    public PayOrderlyConsumer()
//    {
//        try
//        {
//            consumer = new DefaultMQPushConsumer(consumerGroup);
//            consumer.setNamesrvAddr(JmsConfig.NAME_SERVER);
//            // 先进先出
//            consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
//            // 订阅哪个主题的哪个标签
//            consumer.subscribe(JmsConfig.ORDER_TOPIC, "*");
//            // 设置监听器
//            // 给每个队列都加上锁，回调函数在执行的过程中，别的线程无法在同一个队列中取到消息，自然也就无法执行回调函数
//            consumer.registerMessageListener(new MessageListenerOrderly()
//            {
//                @Override
//                public ConsumeOrderlyStatus consumeMessage(List<MessageExt> msgs, ConsumeOrderlyContext context)
//                {
//                    try
//                    {
//                        TimeUnit.SECONDS.sleep((long) (Math.random() * 10));
//                    }
//                    catch (Exception e)
//                    {
//                        e.printStackTrace();
//                    }
//                    MessageExt msg = msgs.get(0);
//                    String body = new String(msg.getBody());
//                    String topic = msg.getTopic();
//                    String tags = msg.getTags();
//                    String msgId = msg.getMsgId();
//                    int queueId = msg.getQueueId();
//                    System.out.println(String.format("%s, body: %s, topic: %s, tags: %s, msgId: %s, queueId: %d", Thread.currentThread().getName(), body, topic, tags, msgId, queueId));
//                    return ConsumeOrderlyStatus.SUCCESS;
//                }
//            });
//            consumer.start();
//        }
//        catch (MQClientException e)
//        {
//            e.printStackTrace();
//        }
//    }
}
