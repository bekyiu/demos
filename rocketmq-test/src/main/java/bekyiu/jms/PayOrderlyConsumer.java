package bekyiu.jms;

import lombok.Getter;
import lombok.Setter;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerOrderly;
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
//            consumer.registerMessageListener(new MessageListenerConcurrently()
//            {
//                @Override
//                public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context)
//                {
//                    try {
//                        TimeUnit.SECONDS.sleep((long) (Math.random() * 10));
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                    MessageExt msg = msgs.get(0);
//                    String body = new String(msg.getBody());
//                    String topic = msg.getTopic();
//                    String tags = msg.getTags();
//                    String msgId = msg.getMsgId();
//                    int queueId = msg.getQueueId();
//                    System.out.println(String.format("%s, body: %s, topic: %s, tags: %s, msgId: %s, queueId: %d",
//                            Thread.currentThread().getName(), body, topic, tags, msgId, queueId));
//                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
//                }
//            });
//            consumer.start();
//        }
//        catch (MQClientException e)
//        {
//            e.printStackTrace();
//        }
//    }
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
            consumer.registerMessageListener(new MessageListenerOrderly()
            {
                @Override
                public ConsumeOrderlyStatus consumeMessage(List<MessageExt> msgs, ConsumeOrderlyContext context)
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
                    System.out.println(String.format("%s, body: %s, topic: %s, tags: %s, msgId: %s, queueId: %d", Thread.currentThread().getName(), body, topic, tags, msgId, queueId));
                    return ConsumeOrderlyStatus.SUCCESS;
                }
            });
            consumer.start();
        }
        catch (MQClientException e)
        {
            e.printStackTrace();
        }
    }
}
