package bekyiu.transaction;

import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;

/**
 * @Author: wangyc
 * @Date: 2020/7/18 12:35
 */
public class TransactionListenerImpl implements TransactionListener
{
    // 发送完消息之后执行
    @Override
    public LocalTransactionState executeLocalTransaction(Message message, Object o)
    {
        System.out.println(Thread.currentThread().getName());
        System.out.println("TransactionListenerImpl.executeLocalTransaction");
        System.out.println(message);
        Integer i = (Integer) o;
        if(i == 1)
        {
            return LocalTransactionState.COMMIT_MESSAGE;
        }
        else if(i == 2)
        {
            return LocalTransactionState.ROLLBACK_MESSAGE;
        }
        else if(i == 3)
        {
            return LocalTransactionState.UNKNOW;
        }
        return null;
    }

    // executeLocalTransaction方法未成功返回，或者返回unknow
    // brock会调用该方法确认消息的状态
    // 由配置的线程池中的线程执行
    @Override
    public LocalTransactionState checkLocalTransaction(MessageExt messageExt)
    {
        System.out.println(Thread.currentThread().getName());
        System.out.println(messageExt);
        return LocalTransactionState.UNKNOW;
    }
}
