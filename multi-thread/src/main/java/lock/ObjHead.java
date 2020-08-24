package lock;

import org.openjdk.jol.info.ClassLayout;

import java.nio.ByteOrder;

/**
 * @Author: wangyc
 * @Date: 2020/8/23 11:17
 */
public class ObjHead
{
    public static void main(String[] args) throws InterruptedException
    {
        Thread.sleep(5000);
        System.out.println(ByteOrder.nativeOrder());
        A a = new A();
        a.hashCode();
        // 前八个字节是 markword 小端存储
        System.out.println(ClassLayout.parseInstance(a).toPrintable());
        synchronized (a)
        {
            System.out.println(ClassLayout.parseInstance(a).toPrintable());

        }
        System.out.println(ClassLayout.parseInstance(a).toPrintable());
    }
}

class A
{
    private int a;
}