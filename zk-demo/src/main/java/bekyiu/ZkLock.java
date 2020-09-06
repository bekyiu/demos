package bekyiu;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @Author: wangyc
 * @Date: 2020/7/18 20:11
 *
 * 使用时多个线程不能混用同一把zkLock，
 * 每个线程必须new自己的锁, 因为该锁是有状态的
 */
public class ZkLock implements Watcher
{
    private ZooKeeper zk;

    private String ROOT = "/lock";

    private String current; // 当前节点

    private String pre; // 前一个节点


    public ZkLock()
    {
        try
        {
            zk = new ZooKeeper("127.0.0.1:2181", 500, this);
            Stat stat = zk.exists(ROOT, false);
            // 判断是否存在 /lock 的节点，用来存放在锁竞争过程中的数据
            if (stat == null)
            {
                //不存在 /lock 创建
                zk.create(ROOT, "0".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            }
        }
        catch (IOException | InterruptedException | KeeperException e)
        {
            e.printStackTrace();
        }
    }

    public void lock(String biz)
    {
        if (tryLock(biz))
        {
            System.out.println(Thread.currentThread().getName() + " 获取锁成功");
            return;
        }
        try
        {
            waitForLock();
            System.out.println(Thread.currentThread().getName() + " 获取锁成功");
        }
        catch (KeeperException | InterruptedException e)
        {
            e.printStackTrace();
        }
    }

    // 等待pre节点被删除 ==上一个获取锁的线程释放锁
    private void waitForLock() throws KeeperException, InterruptedException
    {
        Stat stat = zk.exists(pre, true);
        if (stat != null)
        {
            System.out.println(Thread.currentThread().getName() + "正在等待");
            // 仅仅是为了阻塞
            synchronized (this)
            {
                wait();
            }
        }
    }


    public boolean tryLock(String biz)
    {
        try
        {
            // 创建分类节点，不同的业务可能使用不同的锁 /lock/order
            String bizPath = ROOT + "/" + biz;
            Stat stat = zk.exists(bizPath, false);
            if (stat == null)
            {
                zk.create(bizPath, "0".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            }
            // 创建临时节点 /lock/order/order_00001
            current = zk.create(bizPath + "/" + biz + "_", "0".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
            System.out.println(Thread.currentThread().getName() + "获取到的节点为" + current);
            List<String> list = zk.getChildren(bizPath, false);
            // 对节点排序
            list = list.stream().map(e -> bizPath + "/" + e).sorted(Comparator.naturalOrder()).collect(Collectors.toList());
            // 当前节点的序号
            int i = list.indexOf(current);
            if (i == 0)
            {
                return true;
            }
            else
            {
                // 记录上一个节点
                pre = list.get(i - 1);
                return false;
            }

        }
        catch (KeeperException | InterruptedException e)
        {
            e.printStackTrace();
        }
        return false;
    }


    public void unlock()
    {
        try
        {
            System.out.println(Thread.currentThread().getName() + "释放锁" + current);
            zk.delete(current, -1);
            current = null;
            pre = null;
            zk.close();
        }
        catch (InterruptedException | KeeperException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void process(WatchedEvent event)
    {
        if (event.getType() == Event.EventType.NodeDeleted && event.getPath().startsWith(ROOT))
        {
            synchronized (this)
            {
                notify();
            }
        }
    }

    public static void main(String[] args)
    {

        new Thread(() ->
        {
            ZkLock lock = new ZkLock();
            lock.lock("order");
            lock.unlock();
        }).start();

        new Thread(() ->
        {
            ZkLock lock = new ZkLock();
            lock.lock("order");
            lock.unlock();
        }).start();

        new Thread(() ->
        {
            ZkLock lock = new ZkLock();
            lock.lock("order");
            lock.unlock();
        }).start();

        try
        {
            TimeUnit.SECONDS.sleep(Integer.MAX_VALUE);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }
}
