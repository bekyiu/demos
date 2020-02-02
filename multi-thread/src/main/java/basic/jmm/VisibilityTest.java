package basic.jmm;

/**
 * 可见性测试
 *
 * 什么是jmm:
 *      jmm是对内存和各种cache的一套抽象, 一种读写内存数据的规范
 *      例如工作内存就是对寄存器和L1, L2, 等等cache的抽象
 *      而主内存就是对ram的抽象
 *
 * jmm规定:
 *      1. 所有变量都存储在主内存中, 每个线程有自己独立的工作内存
 *      2. 线程不能直接读写主内存中的变量, 但是可以把自己的工作内存同步到主内存, 或将主内存同步到工作内存
 *      3. 工作内存不共享, 主内存共享, 线程间如果要通信, 要通过主内存
 *
 * 导致可见性问题的原因:
 *      共享变量是存放在主内存中的, 每个线程操作的是自己的工作内存中的拷贝,
 *      所以如果同步的不及时, 就会导致一个线程读到的是旧的值
 *
 * 什么是happens-before:
 *      happens-before规则是用来解决可见性问题的: 在时间上, 动作a发生在动作b之前, b保证能看见a, 这就是happens-before
 *      显然该例子是不满足happens-before的
 *
 * 一些常见的happens-before规则:
 *      1. 单线程内的操作 即前一条语句执行的结果 一定能被后面的语句看到
 *      2. synchronized和lock, 线程a解锁m后, 线程b再加锁m, 此时b一定能看到a解锁前的所有操作
 *      3. volatile, 线程a写一个volatile变量后, 线程b一定能读到 (即不会出现本例中的情况)
 *      并且写volatile变量之前的操作, 也都可以被线程b看到 (即本例只用给b加volatile即可)
 *      4. etc...
 */
public class VisibilityTest
{
    private int a = 1;
    private int b = 2;

    public void change()
    {
        a = 3;
        b = a;
    }

    public void judge()
    {
        // 会发现 a = 1, b = 3 的情况
        // 说明change方法已经被执行了完了 才执行的judge 因为b都等于3了
        // 但是a仍然等于1, 这就说明了可见性的问题 即t1线程改完后没有同步到主内存
        // 或 t2线程在读取的时候没有把主内存同步到工作内存
        System.out.println("a = " + a + ", b = " + b);
    }

    public static void main(String[] args)
    {
        while (true)
        {
            VisibilityTest v = new VisibilityTest();
            // 一个线程写
            Thread t1 = new Thread(v :: change);
            // 一个线程读
            Thread t2 = new Thread(v :: judge);
            t1.start();
            t2.start();
        }
    }
}
