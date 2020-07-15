package bekyiu.jms;

import lombok.Getter;
import lombok.Setter;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.springframework.stereotype.Component;

@Component
@Setter
@Getter
public class PayProducer
{
    private DefaultMQProducer producer;
    private String producerGroup = "pay_p_group";


    public PayProducer()
    {
        producer = new DefaultMQProducer(producerGroup);
        producer.setNamesrvAddr(JmsConfig.NAME_SERVER);
        producer.setRetryTimesWhenSendFailed(2);
        try
        {
            producer.start();
        }
        catch (MQClientException e)
        {
            e.printStackTrace();
        }
    }

    public void shutdown()
    {
        producer.shutdown();
    }

}