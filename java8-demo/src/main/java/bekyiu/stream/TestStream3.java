package bekyiu.stream;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

/**
 *  匹配与查找
 *  allMatch, 检查是否所有元素的元素都匹配条件
 *  anyMatch, 检查是否至少一个元素匹配条件
 *  noneMatch, 没有元素匹配条件 返回true
 *
 *  findFirst, 返回第一个元素
 *  findAny, 返回流中的任意元素
 *  count, 返回流中元素总个数
 *  max, 返回流中最大值
 *  min, ...最小值
 *
 *  reduce, 将流中的元素反复结合起来得到一个值
 *  collect, 将流转换为其他形式
 */
public class TestStream3
{
    public List<Student> stus = Arrays.asList(
            new Student("Louise", 16, 614.0, Status.BUSY),
            new Student("Yukino", 17, 500.0, Status.VACATION),
            new Student("Chitoge", 17, 700.0, Status.BUSY),
            new Student("bekyiu", 22, 700.0, Status.FREE),
            new Student("bekyiu", 22, 7000.0, Status.FREE)
    );
    @Test
    public void test1()
    {
        boolean b = stus.stream().allMatch(s -> s.getStatus() == Status.FREE);
        System.out.println("b = " + b);
        boolean bb = stus.stream().anyMatch(s -> s.getStatus() == Status.FREE);
        System.out.println("bb = " + bb);
        boolean bbb = stus.stream().noneMatch(s -> s.getAge() > 100);
        System.out.println("bbb = " + bbb);
    }
    @Test
    public void test2()
    {
        Optional<Student> first = stus.stream().findFirst();
        System.out.println(first.get());
        Optional<Student> any = stus.parallelStream().findAny();
        System.out.println(any.get());
    }

    @Test
    public void test3()
    {
        Optional<Student> max = stus.stream().max((o1, o2) -> o1.getAge() - o2.getAge());
        System.out.println(max.get());
        Optional<Student> min = stus.stream().min((o1, o2) -> o1.getAge() - o2.getAge());
        System.out.println(min.get());

        // 求最大的工资
        Optional<Double> max1 = stus.stream()
                .map(Student :: getSalary)
                .max(Double :: compareTo);
        System.out.println(max1.get());
    }

    @Test
    public void test4()
    {
        int[] arr = {1, 2, 3, 4, 5};
        // 无初始值
        OptionalInt reduce = Arrays.stream(arr).reduce((x, y) -> x + y);
        System.out.println(reduce.getAsInt());

        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);
        // 有初始值
        Integer reduce1 = list.stream().reduce(10, (x, y) -> x + y);
        System.out.println(reduce1);

        // 计算学生工资总和
        Optional<Double> sum = stus.stream()
                .map(Student :: getSalary)
                .reduce(Double :: sum);
        System.out.println(sum.get());
    }

    @Test
    public void test5()
    {
        // 转换成集合
        Set<String> set = stus.stream().map(Student::getName).collect(Collectors.toSet());
        set.forEach(System.out :: println);
        LinkedHashSet<Student> collect = stus.stream().collect(Collectors.toCollection(() -> new LinkedHashSet<>()));
        collect.forEach(System.out :: println);
        //count
        Long count = stus.stream().collect(Collectors.counting());
        System.out.println(count);
        //avg
        Double avg = stus.stream().collect(Collectors.averagingDouble(e -> e.getSalary()));
        System.out.println(avg);
        //sum
        Double sum = stus.stream().collect(Collectors.summingDouble(e -> e.getSalary()));
        System.out.println(sum);
        //max
        Optional<Double> max = stus.stream().map(Student::getSalary).collect(Collectors.maxBy(Double::compareTo));
        System.out.println(max.get());
        //min
        Optional<Double> min = stus.stream().map(Student::getSalary).min(Double::compareTo);
        System.out.println(min.get());
    }

    @Test
    public void test6()
    {
        // 分组, 类似SQL中的group by
        Map<Status, List<Student>> map = stus.stream().collect(Collectors.groupingBy(Student::getStatus));
        System.out.println("map = " + map);

        // 先按状态分, 再按年龄分
        Map<Status, Map<String, List<Student>>> map2 = stus.stream()
                .collect(Collectors.groupingBy(Student::getStatus,
                        Collectors.groupingBy(s ->
                        {
                            if (s.getAge() <= 20)
                            {
                                return "青少年";
                            }
                            else
                            {
                                return "青年";
                            }
                        })));

        System.out.println("map2 = " + map2);

    }

    @Test
    public void test7()
    {
        // 分区
        Map<Boolean, List<Student>> map = stus.stream().collect(Collectors.partitioningBy(s -> s.getAge() > 20));
        System.out.println("map = " + map);
    }

    @Test
    public void test8()
    {
        String s = stus.stream().map(Student::getName).collect(Collectors.joining(",", "***", "***"));
        System.out.println(s);
    }
}

@Data
@AllArgsConstructor
class Student
{
    private String name;
    private Integer age;
    private Double salary;
    private Status status;
    public Student(){}

}

enum Status
{
    BUSY, VACATION, FREE
}

