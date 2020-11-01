package bekyiu.dataset;

import lombok.Getter;
import org.apache.commons.io.FileUtils;
import org.apache.flink.api.common.JobExecutionResult;
import org.apache.flink.api.common.accumulators.LongCounter;
import org.apache.flink.api.common.functions.*;
import org.apache.flink.api.common.operators.Order;
import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.operators.DataSource;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.core.fs.FileSystem;
import org.apache.flink.util.Collector;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Author: wangyc
 * @Date: 2020/11/1 10:48
 */
public class TransformationTest
{
    private ExecutionEnvironment env;
    private List<Integer> list;
    @Before
    public void init()
    {
        list = Arrays.asList(1, 2, 3, 4, 5);
        env = ExecutionEnvironment.getExecutionEnvironment();
    }
    @Test
    public void map() throws Exception
    {
        DataSource<Integer> dataSource = env.fromCollection(list);
        dataSource.map((MapFunction<Integer, Integer>) value -> {
            System.out.println("====");
            return value + 1;
        }).print();
    }

    @Test
    public void filter() throws Exception
    {
        DataSource<Integer> dataSource = env.fromCollection(list);
        dataSource.filter(new FilterFunction<Integer>()
        {
            @Override
            public boolean filter(Integer value) throws Exception
            {
                return value % 2 == 0;
            }
        }).print();
    }

    @Test
    public void mapPartition() throws Exception
    {
        DataSource<Integer> dataSource = env.fromCollection(list);
        dataSource.mapPartition(new MapPartitionFunction<Integer, Integer>()
        {
            // map是每一次传一个元素去处理
            // mapPartition是一次就传进来所有元素来处理, 可以设置并行度来用多个线程处理
            @Override
            public void mapPartition(Iterable<Integer> values, Collector<Integer> out) throws Exception
            {
                System.out.println("=====" + Thread.currentThread().getName());
                values.forEach(x -> {
                    System.out.println(x + ", " + Thread.currentThread().getName());
                });
            }
        }).setParallelism(2).print();
    }

    @Test
    public void first() throws Exception
    {
        List<Tuple2<Integer, String>> list = new ArrayList<>();
        list.add(new Tuple2<>(1, "Nishino"));
        list.add(new Tuple2<>(1, "Nanase"));
        list.add(new Tuple2<>(1, "Bekyiu"));
        list.add(new Tuple2<>(2, "Bekyiu"));
        list.add(new Tuple2<>(3, "Louise"));
        DataSource<Tuple2<Integer, String>> dataSource = env.fromCollection(list);
        // 取前三条
        dataSource.first(3).print();
        System.out.println("====");
        // 对每个分组取前两条
        dataSource.groupBy(0).first(2).print();
        System.out.println("====");
        // 对每个分组按第一个字段升序排序
        dataSource.groupBy(0).sortGroup(1, Order.ASCENDING).first(3).print();
    }

    @Test
    public void flatMap() throws Exception
    {
        List<String> list = Arrays.asList("nishino,nanase", "bekyiu,Louise", "bekyiu,nanase");
        DataSource<String> dataSource = env.fromCollection(list);
        dataSource.flatMap(new FlatMapFunction<String, String>() {
            // map 对一个输入产生一个输出
            // flatmap 对一个输入可以产生多个输出
            @Override
            public void flatMap(String input, Collector<String> out) throws Exception
            {
                for (String s : input.split(","))
                {
                    out.collect(s);
                }
            }
        }).map(new MapFunction<String, Tuple2<String, Integer>>() {
            @Override
            public Tuple2<String, Integer> map(String value) throws Exception
            {
                return new Tuple2<>(value, 1);
            }
        }).groupBy(0).sum(1).print();
    }

    @Test
    public void join() throws Exception
    {
        List<Tuple3<Integer, String, Integer>> left = new ArrayList<>();
        left.add(new Tuple3<>(1, "nanase", 1));
        left.add(new Tuple3<>(2, "erika", 1));
        left.add(new Tuple3<>(3, "maiyan", 1));
        left.add(new Tuple3<>(4, "asuka", 1));
        left.add(new Tuple3<>(5, "bekyiu", 2));

        List<Tuple2<Integer, String>> right = new ArrayList<>();
        right.add(new Tuple2<>(1, "idol"));
        right.add(new Tuple2<>(2, "programmer"));
        right.add(new Tuple2<>(3, "scientist"));

        DataSource<Tuple3<Integer, String, Integer>> d1 = env.fromCollection(left);
        DataSource<Tuple2<Integer, String>> d2 = env.fromCollection(right);

        // d1的2字段和d2的0字段做inner join
        d1.join(d2).where(2).equalTo(0).print();
    }

    @Test
    public void outJoin() throws Exception
    {
        List<Tuple3<Integer, String, Integer>> left = new ArrayList<>();
        left.add(new Tuple3<>(1, "nanase", 1));
        left.add(new Tuple3<>(2, "erika", 1));
        left.add(new Tuple3<>(3, "maiyan", 1));
        left.add(new Tuple3<>(4, "asuka", 1));
        left.add(new Tuple3<>(5, "bekyiu", 2));

        List<Tuple2<Integer, String>> right = new ArrayList<>();
        right.add(new Tuple2<>(1, "idol"));
        right.add(new Tuple2<>(2, "programmer"));
        right.add(new Tuple2<>(3, "scientist"));

        DataSource<Tuple3<Integer, String, Integer>> d1 = env.fromCollection(left);
        DataSource<Tuple2<Integer, String>> d2 = env.fromCollection(right);

        d1.rightOuterJoin(d2).where(2).equalTo(0)
                .with((JoinFunction<Tuple3<Integer, String, Integer>, Tuple2<Integer, String>, Tuple3<Integer, String, String>>)
                (first, second) -> {
            if (first != null)
            {
                return new Tuple3<>(first.f0, first.f1, second.f1);
            }
            return new Tuple3<>(0, "", second.f1);
        })
                .returns(Types.TUPLE(Types.INT, Types.STRING, Types.STRING)) // 指明返回类型, 不然lambda无法推断
                .print();
    }

    @Test
    public void cross() throws Exception
    {
        // 笛卡尔积
        DataSource<String> d1 = env.fromElements("a", "b");
        DataSource<Integer> d2 = env.fromElements(1, 2);
        d1.cross(d2).print();
    }

    @Test
    public void sink() throws Exception
    {
        String path = "file:///IDEAProjects/demos/flink-test/hello.txt";
        DataSource<Integer> dataSource = env.fromCollection(list);
        // 将处理结果写到文件
        dataSource.writeAsText(path, FileSystem.WriteMode.OVERWRITE);
        env.execute();
    }

    // 计数器
    @Test
    public void counter() throws Exception
    {
        DataSource<Integer> dataSource = env.fromCollection(list);
        DataSet<Integer> info = dataSource.map(new RichMapFunction<Integer, Integer>()
        {
            LongCounter counter = new LongCounter(0);
//            int a = 0;

            // 注册计数器
            @Override
            public void open(Configuration parameters) throws Exception
            {
                super.open(parameters);
                getRuntimeContext().addAccumulator("counter", counter);
            }

            @Override
            public Integer map(Integer value) throws Exception
            {
//                System.out.println(++a + "==");
                counter.add(1);
                return value;
            }
        }).setParallelism(2);
        String path = "file:///IDEAProjects/demos/flink-test/hello.txt";
        info.writeAsText(path, FileSystem.WriteMode.OVERWRITE);

        JobExecutionResult result = env.execute("zz");
        System.out.println((long) result.getAccumulatorResult("counter"));
    }

    // 分布式缓存
    @Test
    public void cache() throws Exception
    {
        DataSource<Integer> dataSource = env.fromCollection(list);
        env.registerCachedFile("file:///IDEAProjects/demos/flink-test/hello.txt", "cache");
        dataSource.map(new RichMapFunction<Integer, Integer>()
        {
            @Override
            public void open(Configuration parameters) throws Exception
            {
                File file = getRuntimeContext().getDistributedCache().getFile("cache");
                FileUtils.readLines(file).forEach(System.out :: println);
                System.out.println("=========");
            }

            @Override
            public Integer map(Integer value) throws Exception
            {
                return value;
            }
        }).setParallelism(3).print();
    }
}
