package bekyiu.jms;

import lombok.Getter;
import lombok.Setter;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: wangyc
 * @Date: 2020/7/14 20:26
 */
@Component
@Setter
@Getter
public class PayConsumer
{
    private DefaultMQPushConsumer consumer;
    private String consumerGroup = "pay_c_group";

    public PayConsumer()
    {
        try
        {
            consumer = new DefaultMQPushConsumer(consumerGroup);
            consumer.setNamesrvAddr(JmsConfig.NAME_SERVER);
            // 先进先出
            consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
            // 订阅哪个主题的哪个标签
            consumer.subscribe(JmsConfig.TOPIC, "*");
            // 设置监听器
            consumer.registerMessageListener(new MessageListenerConcurrently()
            {
                @Override
                public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext ctx)
                {
                    try
                    {
                        // 一般是一条一条的消费
                        MessageExt msg = list.get(0);
                        String body = new String(msg.getBody());
                        String topic = msg.getTopic();
                        String tags = msg.getTags();
                        String msgId = msg.getMsgId();
                        int queueId = msg.getQueueId();
                        System.out.println(String.format("body: %s, topic: %s, tags: %s, msgId: %s, queueId: %d", body, topic,
                                tags, msgId, queueId));
                        // 返回成功，broker会把消息消费掉
                        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                        // 表示稍后再消费
                        return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                    }

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
