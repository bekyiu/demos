package bekyiu.lambda;

import org.junit.Test;

import java.util.Comparator;
import java.util.function.Consumer;

/**
 *  lambda是一个匿名函数, 可以把lambda表达式理解为一段可以传递的代码, 参数列表 -> 函数体
 *  参数列表是lambda实现的接口中的抽象方法中的参数列表, 函数体为此抽象方法的实现
 *
 *  1: 无参数, 无返回值
 *      () -> System.out.println("哈哈");
 *
 *  2: 一个参数, 无返回值
 *      (x) -> System.out.println(x); 或
 *      x -> System.out.println(x);
 *
 *  3: 多个参数, lambda体中有多条语句, 有返回值
 *       Comparator<Integer> comparator = (x, y) -> {
 *             System.out.println(x);
 *             System.out.println(y);
 *             return Integer.compare(x, y);
 *       };
 *
 *  4: lambda中只有一条语句, 有返回值
 *       Comparator<Integer> comparator = (x, y) -> Integer.compare(x, y);
 *
 *  函数式接口: 接口中只有一个抽象方法, 可用@FunctionalInterface检查
 *  lambda表达式需要函数式接口的支持
 */
public class LambdaTest
{

    // 情况1
    @Test
    public void test1()
    {
        // 这个num如果要在匿名内部类中使用, 则是final的
        int num = 1;
        Runnable r1 = new Runnable()
        {
            @Override
            public void run()
            {

                System.out.println("hello" + num);
            }
        };
        r1.run();

        Runnable r2 = () -> System.out.println("haha");
        r2.run();
    }

    // 情况2
    @Test
    public void test2()
    {
        // 此lambda表示式对接口中的accept方法做了实现
        Consumer<String> con = (x) -> System.out.println(x);
        con.accept("nb");
    }

    // 情况3
    @Test
    public void test3()
    {
        Comparator<Integer> comparator = (x, y) -> {
            System.out.println(x);
            System.out.println(y);
            return Integer.compare(x, y);
        };
    }

    // 情况4
    @Test
    public void test4()
    {
        // 省略了{}和return
        Comparator<Integer> comparator = (x, y) -> Integer.compare(x, y);
    }
}
