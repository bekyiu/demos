package bekyiu.controller;

import bekyiu.domain.Order;
import bekyiu.jms.JmsConfig;
import bekyiu.jms.PayProducer;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PayController
{

    @Autowired
    private PayProducer payProducer;

    @GetMapping("/syn")
    public String synSend(String tag) throws InterruptedException, RemotingException, MQClientException, MQBrokerException
    {
        Message m = new Message(JmsConfig.TOPIC, tag, "z", tag.getBytes());
        SendResult result = payProducer.getProducer().send(m);
        System.out.println(result);
        return "ok";
    }

    @GetMapping("/asyn")
    public String asynSend(String msg) throws InterruptedException, RemotingException, MQClientException, MQBrokerException
    {
        Message m = new Message(JmsConfig.TOPIC, "b", "nana", msg.getBytes());
        // 延迟消费
        // 1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h
        // 投递后5s再消费
        m.setDelayTimeLevel(2);
        // 异步发送
        payProducer.getProducer().send(m, new SendCallback()
        {
            @Override
            public void onSuccess(SendResult sendResult)
            {
                System.out.println("发送成功");
                System.out.println(sendResult);
            }

            @Override
            public void onException(Throwable e)
            {
                System.out.println("发送异常");
                e.printStackTrace();
            }
        });
        return "ok";
    }

    // 投递到指定队列
    @GetMapping("/syn/1")
    public String synSend1(String msg) throws InterruptedException, RemotingException, MQClientException, MQBrokerException
    {
        // 该topic有4个队列
        Message m = new Message(JmsConfig.TOPIC, "c", "nana", msg.getBytes());
        SendResult result = payProducer.getProducer().send(m, new MessageQueueSelector()
        {
            /**
             *
             * @param mqs 所有队列
             * @param msg 要发送的消息
             * @param arg 发送的到哪个队列，外面传入
             * @return 要发送到的队列
             */
            @Override
            public MessageQueue select(List<MessageQueue> mqs, Message msg, Object arg)
            {
                int queueId = (Integer) arg;
                return mqs.get(queueId);
            }
        }, 0);
        System.out.println(result);
        return "ok";
    }


    // 发送订单，根据不同的订单id，发送到不同的队列
    @GetMapping("/order")
    public String order() throws InterruptedException, RemotingException, MQClientException, MQBrokerException
    {
        List<Order> list = Order.list();
        for (Order o : list)
        {
            Message m = new Message(JmsConfig.ORDER_TOPIC, "a", o.getId() + "", o.toString().getBytes());
            SendResult result = payProducer.getProducer().send(m, (mqs, msg, arg) -> {
                int queueId = (Integer) arg;
                int index = queueId % mqs.size();
                return mqs.get(index);
            }, o.getId());
            System.out.println(result + " " + o);
        }
        return "ok";
    }
}