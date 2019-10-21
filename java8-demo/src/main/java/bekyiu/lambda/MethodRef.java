package bekyiu.lambda;

import org.junit.Test;

import java.util.function.*;

/**
 *  方法引用, 当lambda体仅由一个函数f完成时, 可以使用方法引用
 *  此时要求f的参数列表和返回值, 要和函数式接口中抽象方法的参数列表和返回值相同
 *
 *  1.
 *      类名 :: 静态方法名
 *
 *  2.
 *      对象 :: 实例方法名
 *
 *  3.
 *      类名 :: 实例方法
 *      当第一个参数作为方法的调用者, 第二个参数作为方法的参数时
 *
 *  构造器引用
 *      类名 :: new
 *
 *  数组引用
 *      type :: new
 */
public class MethodRef
{
    @Test
    public void test1()
    {
        Consumer<String> con = x -> System.out.println(x);
        con.accept("haha");
        // 使用方法引用
        Consumer<String> con1 = System.out::println;
        con1.accept("xixi");
    }

    @Test
    public void test2()
    {
        Employee e = new Employee("Louise", 16, 0.0);
//        Supplier<String> s = () -> e.getName();
        Supplier<String> s = e::getName;
        System.out.println(s.get());
    }

    @Test
    public void test3()
    {
        BiPredicate<String, String> bp = (x, y) -> x.equals(y);
        System.out.println(bp.test("a", "a"));
        // 当第一个参数作为方法的调用者, 第二个参数作为方法的参数时, 才能这么写
        BiPredicate<String, String> bp2 = String::equals;
        System.out.println(bp2.test("ba", "ca"));
    }

    @Test
    public void test4()
    {
        // 构造函数的参数列表, 的和抽象方法的参数列表相同
        Supplier<Employee> s = () -> new Employee();
        System.out.println(s.get());
        s = Employee::new;
        System.out.println(s.get());

        // 数组引用
        Function<Integer, String[]> f = x -> new String[x];
        System.out.println(f.apply(10).length);
        f = String[] :: new;
        System.out.println(f.apply(5).length);
    }
}
