package basic;

public class TestJoin implements Runnable
{
    @Override
    public void run()
    {
        try
        {
            Thread.sleep(2000);
            System.out.println("finish");
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException
    {
        Thread t = new Thread(new TestJoin());
        t.start();
        System.out.println("开始join");
        t.join();
        System.out.println("子线程运行完毕");
    }
}
