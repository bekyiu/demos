package basic;

import java.util.Date;
import java.util.LinkedList;

public class ProducerConsumer
{
    public static void main(String[] args)
    {
        EventStorage storage = new EventStorage();
        Thread producer = new Thread(new Producer(storage));
        Thread consumer = new Thread(new Consumer(storage));
        producer.start();
        consumer.start();
    }
}

class Producer implements Runnable
{
    private EventStorage storage;

    public Producer(EventStorage storage)
    {
        this.storage = storage;
    }

    @Override
    public void run()
    {
        for (int i = 0; i < 100; i++)
        {
            storage.put(new Date());
        }
    }
}

class Consumer implements Runnable
{
    private EventStorage storage;

    public Consumer(EventStorage storage)
    {
        this.storage = storage;
    }

    @Override
    public void run()
    {
        for (int i = 0; i < 100; i++)
        {
            storage.take();
        }
    }
}

class EventStorage
{
    private int size = 10;
    private LinkedList<Date> storage = new LinkedList<>();

    public synchronized void put(Date date)
    {
        // 满了就等待
        while (storage.size() == size)
        {
            try
            {
                wait();
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
        storage.add(date);
        System.out.println("生产了第" + storage.size() + "个产品, " + date);
        // 生产了一个就可以唤醒消费者来消费了
        notifyAll();
    }

    public synchronized Date take()
    {
        while (storage.size() == 0)
        {
            try
            {
                wait();
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
        Date poll = storage.poll();
        System.out.println("取出了一个产品: " + poll + ", 还剩" + storage.size() + "个");
        notifyAll();
        return poll;
    }
}