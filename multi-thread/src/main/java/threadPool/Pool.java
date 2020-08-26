package threadPool;


import lombok.extern.slf4j.Slf4j;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author: wangyc
 * @Date: 2020/8/26 10:23
 */
public class Pool
{
    public static void main(String[] args)
    {
        ThreadPool pool = new ThreadPool(3, 1000,
                TimeUnit.MILLISECONDS, (q, e) -> {
            System.out.println("抛弃任务e: " + e);
        });

        for (int i = 0; i < 15; i++)
        {
            int j = i;
            pool.execute(() -> {
                try
                {
                    Thread.sleep(100);
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
                System.out.println(j + " " + Thread.currentThread().getName());
            });
        }
    }
}

@Slf4j
class ThreadPool
{
    // 任务队列
    private BlockingQueue<Runnable> taskQueue = new BlockingQueue<>();
    // 线程集合
    private HashSet<Worker> workers = new HashSet<>();
    // 核心线程数
    private int coreSize = 4;
    // 获取任务的超时时间
    private long timeout = 1000L;
    private TimeUnit timeUnit = TimeUnit.MILLISECONDS;
    // 拒绝策略
    private RejectPolicy<Runnable> rejectPolicy;

    public ThreadPool(int coreSize, long timeout,
                      TimeUnit timeUnit, RejectPolicy<Runnable> rejectPolicy)
    {
        this.coreSize = coreSize;
        this.timeout = timeout;
        this.timeUnit = timeUnit;
        this.rejectPolicy = rejectPolicy;
    }

    public void execute(Runnable task)
    {
        synchronized (this)
        {
            // 直接创建线程执行
            if(workers.size() < coreSize)
            {
                log.info("直接执行任务: {}", task);
                Worker worker = new Worker(task);
                workers.add(worker);
                worker.start();
            }
            else // 添加到任务队列
            {
                log.info("添加任务到队列: {}", task);
                taskQueue.tryPut(rejectPolicy, task);
            }
        }
    }

    class Worker extends Thread
    {
        private Runnable task;
        public Worker(Runnable task)
        {
            this.task = task;
        }

        @Override
        public void run()
        {
            // 当前任务执行完了就去阻塞队列里取任务
            while(task != null)
            {
                try
                {
                    task.run();
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
                finally
                {
                    task = taskQueue.poll(timeout, timeUnit);
                }
            }
            synchronized (workers)
            {
                log.info("线程结束");
                workers.remove(this);
            }
        }
    }
}

@Slf4j
class BlockingQueue<T>
{
    private Deque<T> queue = new ArrayDeque<>();
    private int capacity = 8;
    private ReentrantLock lock = new ReentrantLock();
    private Condition emptySet = lock.newCondition();
    private Condition fullSet = lock.newCondition();

    public T take()
    {
        lock.lock();
        try
        {
            while(queue.isEmpty())
            {
                log.info("阻塞队列为空, wait...");
                emptySet.await();
            }
            T result = queue.removeFirst();
            fullSet.signalAll();
            return result;
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        finally
        {
            lock.unlock();
        }
        return null;
    }

    public T poll(long timeout, TimeUnit unit)
    {
        lock.lock();
        try
        {
            // 统一为纳秒
            long nanos = unit.toNanos(timeout);
            while(queue.isEmpty())
            {
                log.info("阻塞队列为空, wait time: {}ms", unit.toMillis(nanos));
                if(nanos <= 0)
                {
                    return null;
                }
                nanos = emptySet.awaitNanos(nanos);
            }
            T result = queue.removeFirst();
            fullSet.signalAll();
            return result;
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        finally
        {
            lock.unlock();
        }
        return null;
    }

    public void put(T e)
    {
        lock.lock();
        try
        {
            while(capacity == queue.size())
            {
                log.info("阻塞队列满");
                fullSet.await();
            }
            queue.addLast(e);
            emptySet.signalAll();
        }
        catch (InterruptedException e1)
        {
            e1.printStackTrace();
        }
        finally
        {
            lock.unlock();
        }
    }

    public void tryPut(RejectPolicy<T> rejectPolicy, T e)
    {
        lock.lock();
        try
        {
            if(capacity == queue.size())
            {
                log.info("阻塞队列满, 执行拒绝策略");
                rejectPolicy.reject(this, e);
            }
            else
            {
                queue.addLast(e);
                emptySet.signalAll();
            }
        }
        catch (Exception e1)
        {
            e1.printStackTrace();
        }
        finally
        {
            lock.unlock();
        }
    }

}

@FunctionalInterface
interface RejectPolicy<T>
{
    void reject(BlockingQueue<T> queue, T task);
}