package demo.async;

import demo.sync.SyncProducer;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

import java.util.concurrent.TimeUnit;

/**
 * 异步发送
 */
public class AsyncProducer
{
    public static void main(String[] args) throws Exception
    {
        // 实例化消息生产者Producer
        DefaultMQProducer producer = new DefaultMQProducer("nogizaka46");
        // 设置NameServer的地址
        producer.setNamesrvAddr("39.106.156.11:9876");
        // 启动Producer实例
        producer.start();
        producer.setRetryTimesWhenSendAsyncFailed(0);
        for (int i = 0; i < 5; i++)
        {
            // 创建消息，并指定Topic，Tag和消息体
            Message msg = new Message("TopicTest",
                    "top_async",
                    "OrderID188",
                    SyncProducer.members[i].getBytes(RemotingHelper.DEFAULT_CHARSET));
            // SendCallback接收异步返回结果的回调
            producer.send(msg, new SendCallback()
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
        }
        // 如果不再发送消息，关闭Producer实例。
        // shutdown就接受不了消息了
        // producer.shutdown();
        TimeUnit.SECONDS.sleep(1000);
    }
}