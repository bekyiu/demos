package bekyiu.stream;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 *  Stream（流）是一个来自数据源的元素队列并支持聚合操作
 *  元素是特定类型的对象，形成一个队列。 Java中的Stream并不会存储元素，而是按需计算。
 *  修改流中的数据并不会影响源
 *
 *  1. 创建Stream
 *  2. 中间操作
 *  3. 终止操作
 */
public class TestStream
{
    // 创建Stream的几种方式
    @Test
    public void test1()
    {
        //1.
        List<String> list = new ArrayList<>();
        Stream<String> stream1 = list.stream();

        //2
        IntStream stream2 = Arrays.stream(new int[10]);

        //3
        Stream<String> stream3 = Stream.of("a", "v", "c");

        //4 无限流
        Stream.iterate(0, x -> x + 2).limit(10).forEach(System.out :: println);

        Stream.generate(Math :: random)
                .limit(10)
                .forEach(System.out :: println);
    }


}
