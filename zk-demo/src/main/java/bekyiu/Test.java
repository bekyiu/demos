package bekyiu;

import org.apache.zookeeper.*;

import java.io.IOException;
import java.util.List;

/**
 * @Author: wangyc
 * @Date: 2020/7/18 19:28
 * <p>
 * ZooKeeper CreateMode节点类型说明：
 * 1.PERSISTENT：持久型
 * 2.PERSISTENT_SEQUENTIAL：持久顺序型
 * 3.EPHEMERAL：临时型
 * 4.EPHEMERAL_SEQUENTIAL：临时顺序型
 * <p>
 * 1、2种类型客户端断开后不会消失
 * 3、4种类型客户端断开后超时时间内没有新的连接节点将会消失
 * <p>
 * ZooKeeper ZooDefs.Ids权限类型说明：
 * OPEN_ACL_UNSAFE：完全开放的ACL，任何连接的客户端都可以操作该属性znode
 * CREATOR_ALL_ACL：只有创建者才有ACL权限
 * READ_ACL_UNSAFE：只能读取ACL
 * <p>
 * ZooKeeper EventType事件类型说明：
 * NodeCreated：节点创建
 * NodeDataChanged：节点的数据变更
 * NodeChildrenChanged：子节点的数据变更
 * NodeDeleted：子节点删除
 * <p>
 * ZooKeeper CreateMode节点类型说明：
 * 1.PERSISTENT：持久型
 * 2.PERSISTENT_SEQUENTIAL：持久顺序型
 * 3.EPHEMERAL：临时型
 * 4.EPHEMERAL_SEQUENTIAL：临时顺序型
 * <p>
 * 1、2种类型客户端断开后不会消失
 * 3、4种类型客户端断开后超时时间内没有新的连接节点将会消失
 * <p>
 * ZooKeeper ZooDefs.Ids权限类型说明：
 * OPEN_ACL_UNSAFE：完全开放的ACL，任何连接的客户端都可以操作该属性znode
 * CREATOR_ALL_ACL：只有创建者才有ACL权限
 * READ_ACL_UNSAFE：只能读取ACL
 * <p>
 * ZooKeeper EventType事件类型说明：
 * NodeCreated：节点创建
 * NodeDataChanged：节点的数据变更
 * NodeChildrenChanged：子节点的数据变更
 * NodeDeleted：子节点删除
 * <p>
 * ZooKeeper KeeperState状态类型说明：
 * Disconnected：连接失败
 * SyncConnected：连接成功
 * AuthFailed：认证失败
 * Expired：会话过期
 * None：初始状态
 */
public class Test implements Watcher
{
    private static ZooKeeper zk;

    public static void main(String[] args) throws InterruptedException, IOException
    {
        zk = new ZooKeeper("127.0.0.1:2181", 1000, new Test());
        Thread.sleep(Integer.MAX_VALUE);
    }

    public void process(WatchedEvent event)
    {
        System.out.println("=============================================");
        try
        {
            System.out.println(event);
            // 子节点列表变化 even的处理
            if (event.getType() == Event.EventType.NodeChildrenChanged)
            {
                // 再次获取子节点列表
                // event.getPath()返回 哪个znode的子节点列表发生了变化
                List<String> children = zk.getChildren(event.getPath(), true);
                System.out.println("NodeChildrenChanged children=" + children);
            }
            else if (event.getType() == Event.EventType.NodeDeleted)
            {
                List<String> children = zk.getChildren(event.getPath(), true);
                System.out.println("删除儿子后: " + children);
            }
            else if (event.getState() == Event.KeeperState.SyncConnected)
            {
                getChildrenSync();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    //get children ,synchronized
    private void getChildrenSync() throws KeeperException, InterruptedException
    {
        System.out.println("Get Childran in sync mode");
        //false ,不关注子节点列表的变更事件（不注册watcher）
        List<String> children = zk.getChildren("/", true);
        System.out.println("Children list of /: " + children);

    }

    //create node, synchronized
    private void createNodeSync() throws KeeperException, InterruptedException
    {
        System.out.println("Create node with Sync mode");
        String path = zk.create("/lock/lock1", "123".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        System.out.println("New Node added: " + path);
    }
}
