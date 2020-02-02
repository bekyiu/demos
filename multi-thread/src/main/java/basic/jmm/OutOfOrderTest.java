package basic.jmm;

import java.util.concurrent.CountDownLatch;

/**
 * 测试指令重排
 */
public class OutOfOrderTest
{
    public static int a = 0;
    public static int b = 0;
    public static int x = 0;
    public static int y = 0;

    public static void main(String[] args) throws InterruptedException
    {
        int i = 0;
        while (true)
        {
            i++;
            a = 0;
            b = 0;
            x = 0;
            y = 0;
            CountDownLatch latch = new CountDownLatch(1);
            Thread t1 = new Thread(() ->
            {
                try
                {
                    latch.await();
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
                // 这两句可以重排
                a = 1;
                x = b;
            });
            Thread t2 = new Thread(() ->
            {
                try
                {
                    latch.await();
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
                // 这两句可以重排
                b = 1;
                y = a;
            });

            t1.start();
            t2.start();
            latch.countDown();
            t1.join();
            t2.join();

            // 发生指令重排 i = 218612: x = 0, y = 0
            if (x == 0 && y == 0)
            {
                System.out.println("i = " + i + ": x = " + x + ", y = " + y);
                break;
            }
            System.out.println("i = " + i + ": x = " + x + ", y = " + y);
        }
    }
}
