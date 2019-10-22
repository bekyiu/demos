package bekyiu.stream;

import org.junit.Test;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class TestStream4
{
    /**
     *  1.
     *  给定一个数字列表，如何返回一个由每个数的平方构成的列表呢?
     *  给定[1,2,3,4,5]，
     *  ,应该返回[1, 4, 9, 16，25]。
     */
    @Test
    public void test1()
    {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6);
        List<Integer> collect = list.stream().map(x -> x * x).collect(Collectors.toList());
        collect.forEach(System.out :: println);
    }
    public List<Student> stus = Arrays.asList(
            new Student("Louise", 16, 614.0, Status.BUSY),
            new Student("Yukino", 17, 500.0, Status.VACATION),
            new Student("Chitoge", 17, 700.0, Status.BUSY),
            new Student("bekyiu", 22, 700.0, Status.FREE),
            new Student("bekyiu", 22, 7000.0, Status.FREE)
    );

    /**
     *  用map, reduce计算有多少个学生
     */
    @Test
    public void test2()
    {
        Optional<Integer> sum = stus.stream().map(s -> 1).reduce(Integer::sum);
        System.out.println("sum = " + sum);
    }

    /**
     *  并行流
     */
    @Test
    public void test3()
    {
        Instant now = Instant.now();
        // 右开, rangeClose是右闭
        // long sum = LongStream.range(0, 10000000001L).sum();
        long sum = LongStream.range(0, 10000000001L).parallel().sum();
        System.out.println(sum);
        Instant end = Instant.now();
        System.out.println("time: " + Duration.between(now, end).toMillis()); // 串行: 6342, 并行: 3227

    }
}
