package bekyiu.stream;

import lombok.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

/**
 *  filter, 从流中排除某些元素
 *  limit, 截断流, 使其元素不超过给定的数量
 *  skip(n), 跳过前n个元素, 若流中元素不足n个, 返回一个空流
 *  distinct, 根据hashcode和equals去重
 *
 *  map, 接收一个lambda, 将流中的每个元素用函数处理, 并将函数的返回值组成一个新流
 *  flatMap, 接收一个lambda, 将流中的每个元素用函数处理, 并将函数的返回值组成一个新流, 并且把新流中的所有流
 *  连接成一个大流
 *
 *  sorted() 自然排 Comparable
 *  sorted(Comparator com) 定制排序
 */
public class TestStream2
{
    public List<Employee> emps = Arrays.asList(
            new Employee("Louise", 16, 614.0),
            new Employee("Yukino", 17, 500.0),
            new Employee("Chitoge", 17, 700.0),
            new Employee("bekyiu", 22, 700.0),
            new Employee("bekyiu", 22, 7000.0)
    );
    @Test
    public void test1()
    {
        Stream<Employee> stream = emps.stream().filter(e -> {
            System.out.println("中间操作");
            return e.getSalary() > 600.0;
        });
        // 懒加载 只有执行中止操作的时候, 中间操作才会执行
        stream.forEach(System.out :: println);
    }

    @Test
    public void test2()
    {
        // 找到符合条件的两个过后, 就不会继续迭代了
        emps.stream().filter(e ->  {
            System.out.println("中间操作");
            return e.getSalary() > 600.0;
        }).limit(2).forEach(System.out :: println);

        System.out.println("------------------");
        // 先取前两个, 再过滤, 顺序是会有影响的
        emps.stream().limit(2).filter(e -> e.getSalary() > 600.0).forEach(System.out :: println);
    }

    @Test
    public void test3()
    {
        emps.stream().skip(2).forEach(System.out :: println);
    }

    @Test
    public void test4()
    {
        emps.stream().distinct().forEach(System.out :: println);
    }

    @Test
    public void test5()
    {
        // map, 对给定流中的每个元素进行处理, 构成一个新的流
        Stream<String> stream = emps.stream().map(Employee :: getName);
        stream.forEach(System.out :: println);
        System.out.println("----------------------");
        List<String> list = Arrays.asList("aaa", "bbb", "ccc");
        // 注意泛型
        // map会构成一个新的流, 新的流的内容就是lambda的返回值
        // 相当于是{{a, a, a}, {b, b, b}, {c, c, c}}
        Stream<Stream<Character>> stream1 = list.stream().map(TestStream2::toStream);
        stream1.forEach(s -> s.forEach(System.out :: println));
    }

    @Test
    public void test6()
    {
        List<String> list = Arrays.asList("aaa", "bbb", "ccc");
        // 相当于是{a, a, a, b, b, b, c, c, c}
        Stream<Character> stream1 = list.stream().flatMap(TestStream2::toStream);
        stream1.forEach(System.out :: println);
    }

    // 将str转换成流
    public static Stream<Character> toStream(String str)
    {
        List<Character> list = new ArrayList<>();
        for (Character ch : str.toCharArray())
        {
            list.add(ch);
        }
        return list.stream();
    }

    // 排序
    @Test
    public void test7()
    {
        emps.stream().sorted((o1, o2) -> {
            if(o1.getSalary().equals(o2.getSalary()))
            {
                return o1.getName().compareTo(o2.getName());
            }
            return o1.getSalary().compareTo(o2.getSalary());
        }).forEach(System.out :: println);

        // 原集合没有变化
        System.out.println(emps);
    }
}


@Setter
@Getter
@ToString
@AllArgsConstructor
class Employee
{
    private String name;
    private Integer age;
    private Double salary;
    public Employee(){}

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return Objects.equals(name, employee.name) &&
                Objects.equals(age, employee.age) &&
                Objects.equals(salary, employee.salary);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(name, age, salary);
    }
}
