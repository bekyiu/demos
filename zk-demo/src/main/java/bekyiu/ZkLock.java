package bekyiu;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.stream.Collectors;

/**
 * @Author: wangyc
 * @Date: 2020/7/18 20:11
 */
public class ZkLock implements Lock, Watcher
{
    private ZooKeeper zk;

    private String ROOT = "/lock";

    private String current; // 当前节点

    private String pre; // 前一个节点

    private CountDownLatch countDownLatch;

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

    @Override
    public void lock()
    {
        if (tryLock())
        {
            System.out.println(Thread.currentThread().getName() + " 获取锁成功");
            return;
        }
        try
        {
            waitForLock();
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
            countDownLatch = new CountDownLatch(1);
            // 阻塞，pre被删除后唤醒
            countDownLatch.await();
        }
    }

    @Override
    public void lockInterruptibly() throws InterruptedException
    {

    }

    @Override
    public boolean tryLock()
    {
        // 创建临时有序的节点
        try
        {
            current = zk.create(ROOT + "/", "0".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
            System.out.println(Thread.currentThread().getName() + "获取到的节点为" + current);
            List<String> list = zk.getChildren(ROOT, false);
            // 对节点排序
            list = list.stream().map(e -> ROOT + "/" + e).sorted(Comparator.naturalOrder()).collect(Collectors.toList());
            // 当前节点的序号
            int i = list.indexOf(current);
            if (i == 0)
            {
                return true; // 拿到锁了
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

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException
    {
        return false;
    }

    @Override
    public void unlock()
    {
        try
        {
            System.out.println(Thread.currentThread().getName() + "释放锁" + current);
            zk.delete(current, -1);
            current = null;
            zk.close();
        }
        catch (InterruptedException | KeeperException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public Condition newCondition()
    {
        return null;
    }

    @Override
    public void process(WatchedEvent event)
    {
        if(countDownLatch != null)
        {
            countDownLatch.countDown();
        }
//        if(event.getType() == Event.EventType.NodeChildrenChanged)
//        {
//            if(countDownLatch != null)
//            {
//                countDownLatch.countDown();
//            }
//        }
    }

    public static void main(String[] args)
    {
        for(int i = 0; i < 100; i++)
        {
            new Thread(new Task(new ZkLock()),"线程" + i).start();
        }
    }
}

class Task implements Runnable
{
    public static int a = 0;
    private ZkLock lock;
    public Task(ZkLock lock)
    {
        this.lock = lock;
    }

    @Override
    public void run()
    {
//        lock.lock();
        a++;
        System.out.println(a);
//        lock.unlock();
    }
}
