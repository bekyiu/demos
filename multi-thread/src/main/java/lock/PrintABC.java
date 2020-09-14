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
//        PrintABC print = new PrintABC(1, 5);
//        new Thread(() -> {
//            print.print("a", 1, 2);
//        }).start();
//
//        new Thread(() -> {
//            print.print("b", 2, 3);
//        }).start();
//
//        new Thread(() -> {
//            print.print("c", 3, 1);
//        }).start();
        strCompare();
    }

    public static void intCompare()
    {
        Integer a = new Integer(614);
        Integer b = new Integer(614);
        Integer c = new Integer(2);
        Integer d = 2;
        // false
        System.out.println(a == b);
        // false
        System.out.println(c == d);

        // 调用valueOf方法自动装箱
        Integer e = 777;
        Integer f = 777;
        // false
        System.out.println(e == f);

        // 缓存范围[-128, 127]
        System.out.println(Integer.valueOf(-128) == Integer.valueOf(-128));
        System.out.println(Integer.valueOf(-129) == Integer.valueOf(-129));
    }

    public static void strCompare()
    {
        String a = new String("aaa");
        String b = new String("aaa");
        // false
        System.out.println(a == b);

        String c = "cde";
        String d = "fg";
        String haha = "cde" + "fg";
        String f = c + d; // 理解为返回一个新的对象
        String e = "cdefg";
        // false
        System.out.println(f == e);
        // true
        System.out.println(haha == e);
        // false
        System.out.println(haha == f);
    }
}
