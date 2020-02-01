package basic;

/**
 * 统一异常处理
 *
 * try catch是只能catch到本线程抛出的异常
 * 如果子线程抛出异常, 父线程是catch不到的, 自然也就无法在父线程中统一处理异常
 * 解决方案: 实现 Thread.UncaughtExceptionHandler 接口
 *      当子线程抛出异常后就会去执行接口中的uncaughtException方法
 */
public class TestUncaughtExceptionHandler
{
    public static void main(String[] args)
    {
        Thread.setDefaultUncaughtExceptionHandler(new MyUncaughtExceptionHandler("哈哈"));
        Thread t1 = new Thread(() ->
        {
            throw new RuntimeException("err1");
        });
        Thread t2 = new Thread(() ->
        {
            throw new RuntimeException("err2");
        });
        t1.start();
        t2.start();
    }
}

class MyUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler
{
    private String name;
    public MyUncaughtExceptionHandler(String handlerName)
    {
        this.name = handlerName;
    }
    @Override
    public void uncaughtException(Thread t, Throwable e)
    {
        // 返回true, 说明处理异常还是在发生错误的线程中处理
        // 好处是可以统一处理, 而不用每个线程都去写try catch
        System.out.println(Thread.currentThread() == t);
        System.out.println(t.getName() + "出现异常: " + e.getMessage() + ", handler: " + name);
    }
}
