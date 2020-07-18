package bekyiu.transaction;

import bekyiu.jms.JmsConfig;
import lombok.Getter;
import lombok.Setter;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.springframework.stereotype.Component;

import java.util.concurrent.*;

/**
 *
 */
@Component
@Setter
@Getter
public class TransProducer
{
    private TransactionMQProducer producer;
    private String producerGroup = "trans_group";
    private TransactionListener transactionListener = new TransactionListenerImpl();

    public TransProducer()
    {
        producer = new TransactionMQProducer(producerGroup);
        ExecutorService executorService = new ThreadPoolExecutor(2, 5,
                100, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(2000),
                new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread thread = new Thread(r);
                thread.setName("client-transaction-msg-check-thread");
                return thread;
            }
        });
        producer.setNamesrvAddr(JmsConfig.NAME_SERVER);
        producer.setExecutorService(executorService);
        producer.setTransactionListener(transactionListener);
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