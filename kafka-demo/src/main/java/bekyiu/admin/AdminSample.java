package bekyiu.admin;

import org.apache.kafka.clients.admin.*;
import org.junit.Before;
import org.junit.Test;

import java.util.*;
import java.util.concurrent.ExecutionException;

/**
 * @Author: wangyc
 * @Date: 2020/10/18 15:08
 * AdminClient API 允许管理和检测topic broker以及其他kafka对象
 */
public class AdminSample
{
    private AdminClient adminClient;

    // 创建一个客户端
    @Before
    public void adminClient()
    {
        Properties config = new Properties();
        config.setProperty(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, "47.108.119.93:9092");
        adminClient = AdminClient.create(config);
    }

    @Test
    public void createTopic() throws ExecutionException, InterruptedException
    {
        NewTopic t = new NewTopic("nishino-nanase", 1, new Short("1"));
        CreateTopicsResult topics = adminClient.createTopics(Collections.singleton(t));
        System.out.println(topics.all().get());
    }

    @Test
    public void listTopics1() throws ExecutionException, InterruptedException
    {
        ListTopicsResult topicsResult = adminClient.listTopics();
        topicsResult.names().get().forEach(System.out :: println);
    }

    @Test
    public void listTopics2() throws ExecutionException, InterruptedException
    {
        ListTopicsOptions options = new ListTopicsOptions();
        options.listInternal(true);
        ListTopicsResult topicsResult = adminClient.listTopics(options);
        Collection<TopicListing> listings = topicsResult.listings().get();
        listings.forEach(System.out :: println);
        System.out.println("==================");
        Map<String, TopicListing> map = topicsResult.namesToListings().get();
        System.out.println(map);
    }

    @Test
    public void delTopics() throws ExecutionException, InterruptedException
    {
        DeleteTopicsResult result = adminClient.deleteTopics(Collections.singleton("first-topic"));
        System.out.println(result.all().get());
    }

    // 描述信息
    @Test
    public void describeTopics() throws ExecutionException, InterruptedException
    {
        DescribeTopicsResult result = adminClient.describeTopics(Collections.singleton("nishino-nanase"));
        Map<String, TopicDescription> map = result.all().get();
        map.forEach((k, v) -> {
            System.out.println("key: " + k);
            System.out.println("value: " + v);
        });
    }

    // 增加partition
    @Test
    public void incrPartitions() throws ExecutionException, InterruptedException
    {
        // 增加至2个
        NewPartitions p = NewPartitions.increaseTo(2);
        Map<String, NewPartitions> map = new HashMap<>();
        map.put("nishino-nanase", p);
        CreatePartitionsResult result = adminClient.createPartitions(map);
        // 等执行完再结束方法, 不然创建无效
        System.out.println(result.all().get());
    }
}
