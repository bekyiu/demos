package bekyiu.producer;

import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;

import java.util.Map;

/**
 * @Author: wangyc
 * @Date: 2020/10/18 21:36
 *
 * 自定义负载均衡
 */
public class SamplePartition implements Partitioner
{
    @Override
    public int partition(String topic, Object key, byte[] keyBytes, Object value, byte[] valueBytes, Cluster cluster)
    {
        String k = key.toString();
        int kInt = Integer.parseInt(k.substring(k.length() - 1));
        // 偶数发到0, 奇数发到1
        return kInt % 2;
    }

    @Override
    public void close()
    {

    }

    @Override
    public void configure(Map<String, ?> configs)
    {

    }
}
