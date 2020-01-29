package basic;

/**
 * 如何正确的停止线程
 */
public class StopThread
{
    // 线程内不存在阻塞时, 停用线程
    public static void fun1() throws InterruptedException
    {
        Thread t1 = new Thread(() ->
        {
            int num = 0;
            while (num < Integer.MAX_VALUE)
            {
                // 收到信号后为true
                if (Thread.currentThread().isInterrupted())
                {
                    break;
                }
                System.out.println(num++);
            }
            System.out.println("跳出while");
            System.out.println("跳出while");
            System.out.println("跳出while");
            System.out.println("跳出while");
        });
        t1.start();

        Thread.sleep(1000);

        // 该方法仅仅是发出一个想要中断线程的信号, 具体能不能中断要看线程中处不处理
        t1.interrupt();
    }

    // 如果线程被中断后还要sleep就会抛出异常
    public static void fun2() throws InterruptedException
    {
        Thread t1 = new Thread(() ->
        {
            int num = 0;
            while (num < Integer.MAX_VALUE)
            {
                if (Thread.currentThread().isInterrupted())
                {
                    break;
                }
                System.out.println(num++);
            }
            System.out.println("跳出while");
            System.out.println("跳出while");
            System.out.println("跳出while");
            System.out.println("跳出while");
            try
            {
                Thread.sleep(5000);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        });
        t1.start();

        Thread.sleep(1000);

        t1.interrupt();
    }


    // 如果线程在sleep中途被中断 也会抛出异常
    public static void fun3() throws InterruptedException
    {
        Thread t1 = new Thread(() ->
        {
            System.out.println("线程开始");
            try
            {
                Thread.sleep(10000);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
            System.out.println("线程结束");
        });
        t1.start();

        Thread.sleep(1000);

        t1.interrupt();
    }

    // 所以利用fun2和fun3的特点就可以跳出while中断线程
    // 注意try是包裹了整个while的, 不然线程不会被中断
    public static void fun4() throws InterruptedException
    {
        Thread t1 = new Thread(() ->
        {
            int num = 0;
            try
            {
                while (num < Integer.MAX_VALUE)
                {
                    // 不用判断信号
                    System.out.println(num++);
                    Thread.sleep(100);
                }
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
            System.out.println("跳出while");
            System.out.println("跳出while");
            System.out.println("跳出while");
            System.out.println("跳出while");
        });
        t1.start();

        Thread.sleep(1000);

        t1.interrupt();
    }

    // 抛出异常后会清除中断标记
    public static void fun5() throws InterruptedException
    {
        Thread t1 = new Thread(() ->
        {
            int num = 0;
            while (!Thread.currentThread().isInterrupted() && num < Integer.MAX_VALUE)
            {
                System.out.println(num++);
                try
                {
                    Thread.sleep(100);
                }
                catch (InterruptedException e)
                {
                    // 能走到这里说明 已经收到中断信号了
                    // 但是isInterrupted() 会返回false, 因为这个标记位在处理后会被清除
                    System.out.println(Thread.currentThread().isInterrupted());
                    e.printStackTrace();
                    // 重新置为中断标记位, 这样下次循环才不会进入
                    Thread.currentThread().interrupt();
                }
            }
            System.out.println("跳出while");
            System.out.println("跳出while");
            System.out.println("跳出while");
            System.out.println("跳出while");
        });
        t1.start();

        Thread.sleep(1000);

        t1.interrupt();
    }

    public static void main(String[] args) throws InterruptedException
    {
//        fun1();
//        fun2();
//        fun3();
//        fun4();
        fun5();
    }
}
