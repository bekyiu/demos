package bekyiu.controller;

import bekyiu.jms.JmsConfig;
import bekyiu.jms.PayProducer;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PayController
{

    @Autowired
    private PayProducer payProducer;

    @GetMapping("/pay")
    public String send(String msg) throws InterruptedException, RemotingException, MQClientException, MQBrokerException
    {
        Message m = new Message(JmsConfig.TOPIC, "a", msg.getBytes());
        SendResult result = payProducer.getProducer().send(m);
        System.out.println(result);
        return "ok";
    }
}