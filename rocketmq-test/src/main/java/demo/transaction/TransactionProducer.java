package demo.transaction;

import org.apache.rocketmq.client.producer.*;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.remoting.common.RemotingHelper;

import java.util.concurrent.Executors;

/**
 * @Author: wangyc
 * @Date: 2020/9/6 17:59
 * <p>
 * 事务消息生产者
 */
public class TransactionProducer
{
    public static String[] members = {"白", "桥", "七"};

    public static void main(String[] args) throws Exception
    {
        // 实例化消息生产者Producer
        TransactionMQProducer producer = new TransactionMQProducer("nogizaka46");
        // 设置NameServer的地址
        producer.setNamesrvAddr("39.106.156.11:9876");
        //
        producer.setExecutorService(Executors.newFixedThreadPool(5));
        // 设置事务监听器
        producer.setTransactionListener(new TransactionListener()
        {
            // 执行本地事务
            @Override
            public LocalTransactionState executeLocalTransaction(Message msg, Object arg)
            {
                int i = (int) arg;
                String body = new String(msg.getBody());
                if (i == 0)
                {
                    System.out.println("提交: " + body);
                    return LocalTransactionState.COMMIT_MESSAGE;
                }
                else if (i == 1)
                {
                    System.out.println("回滚: " + body);
                    return LocalTransactionState.ROLLBACK_MESSAGE;
                }
                System.out.println("准备回查: " + body);
                return LocalTransactionState.UNKNOW;
            }

            // 回查接口
            @Override
            public LocalTransactionState checkLocalTransaction(MessageExt msg)
            {
                System.out.println("回查接口被调用: " + new String(msg.getBody()));
                return LocalTransactionState.COMMIT_MESSAGE;
            }
        });
        // 启动Producer实例
        producer.start();
        for (int i = 0; i < 3; i++)
        {
            // 创建消息，并指定Topic，Tag和消息体
            Message msg = new Message("TopicTest" /* Topic */,
                    "top" /* Tag */,
                    members[i].getBytes(RemotingHelper.DEFAULT_CHARSET) /* Message body */
            );
            // 发送半消息
            SendResult sendResult = producer.sendMessageInTransaction(msg, i);
            // 通过sendResult返回消息是否成功送达
            System.out.println(sendResult);
        }
        // 如果不再发送消息，关闭Producer实例。
//        producer.shutdown();
    }
}
