package lock;

/**
 * @Author: wangyc
 * @Date: 2020/8/24 16:43
 *
 * 三个线程轮流打印abc abc abc ...
 */
public class PrintABC
{
    // 1打印a, 2打印b, 3打印c
    private int flag;
    private int loop;

    public PrintABC(int flag, int loop)
    {
        this.flag = flag;
        this.loop = loop;
    }


    public void print(String str, int flag, int nextFlag)
    {
        for (int i = 0; i < loop; i++)
        {
            synchronized (this)
            {
                while(this.flag != flag)
                {
                    try
                    {
                        this.wait();
                    }
                    catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                }
                System.out.print(str);
                this.flag = nextFlag;
                this.notifyAll();
            }
        }
    }


    public static void main(String[] args)
    {
        PrintABC print = new PrintABC(1, 5);
        new Thread(() -> {
            print.print("a", 1, 2);
        }).start();

        new Thread(() -> {
            print.print("b", 2, 3);
        }).start();

        new Thread(() -> {
            print.print("c", 3, 1);
        }).start();
    }
}
