package bekyiu.stream;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.junit.Test;
import org.junit.experimental.theories.suppliers.TestedOn;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

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

