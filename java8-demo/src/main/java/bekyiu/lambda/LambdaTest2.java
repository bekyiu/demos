package bekyiu.lambda;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 *  lambda表达式练习
 */
public class LambdaTest2
{

    public static List<Employee> emps = Arrays.asList(
            new Employee("Louise", 16, 614.0),
            new Employee("Yukino", 17, 500.0),
            new Employee("Chitoge", 17, 700.0),
            new Employee("bekyiu", 22, 7000.0)
    );
    /**
     *  1. 调用Collections.sort()方法, 比较Employee, 先按年龄比, 然后按姓名比
     */
    @Test
    public void test1()
    {
        Collections.sort(emps, (e1, e2) -> {
            if(e1.getAge() != e2.getAge())
            {
                return e1.getAge() - e2.getAge();
            }
            return e1.getName().compareTo(e2.getName());
        });
        System.out.println(emps);
    }
    /**
     *  2.
     *  申明函数式接口, 接口中声明抽象方法 String getValue(String)
     *  申明类TestLambda, 类中编写方法使用接口作为参数, 将一个字符串转换成大写, 并作为方法的返回值
     *  再将一个字符串的第2个和第4个索引位置进行截取子串
     */
    @Test
    public void test2()
    {
        String str = "Louise";
        System.out.println(TestLambda.func(str, s -> s.toUpperCase()));
        System.out.println(TestLambda.func(str, s -> s.substring(2, 5)));
    }

    /**
     *  3.
     *  声明一个带两个泛型的函数式接口<T, R> T为参数, R为返回值
     *  接口中申明对应抽象方法
     *  在TestLambda中申明方法, 使用接口作为参数, 计算两个long参数的和与乘积
     */
    @Test
    public void test3()
    {
        System.out.println(TestLambda.num(10L, 10L, (x, y) -> x + y));
        System.out.println(TestLambda.num(10L, 10L, (x, y) -> x * y));
    }
}

@FunctionalInterface
interface Fun
{
    String getValue(String str);
}

@FunctionalInterface
interface Fun2<T, R>
{
    R getValue(T o1, T o2);
}

class TestLambda
{
    public static String func(String str, Fun fun)
    {
        return fun.getValue(str);
    }
    public static Long num(Long l1, Long l2, Fun2<Long, Long> fun)
    {
        return fun.getValue(l1, l2);
    }
}

@Data
@AllArgsConstructor
class Employee
{
    private String name;
    private Integer age;
    private Double salary;
    public Employee(){}
}
