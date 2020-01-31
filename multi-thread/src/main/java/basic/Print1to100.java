package basic;

/**
 * 两个线程交替打印0~100的奇偶数
 */
public class Print1to100 implements Runnable
{
    private int count = 0;

    public static void main(String[] args) throws InterruptedException
    {
        Print1to100 task = new Print1to100();
        Thread t1 = new Thread(task, "偶数");
        Thread t2 = new Thread(task, "奇数");
        t1.start();
        Thread.sleep(100);
        t2.start();
    }

    @Override
    public void run()
    {
        while(count <= 100)
        {
            synchronized (this)
            {
                System.out.println(Thread.currentThread().getName() + ", " + count++);
                try
                {
                    notify();
                    // 当打印100的线程执行时, count变为101, 整个程序应该结束
                    // 如果wait了, 就没有人来唤醒了
                    if(count <= 100)
                    {
                        wait();
                    }
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }

        }
    }
}
