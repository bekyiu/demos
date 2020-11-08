package bekyiu.datastream;

import lombok.SneakyThrows;
import org.apache.flink.streaming.api.collector.selector.OutputSelector;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: wangyc
 * @Date: 2020/11/8 10:55
 */
public class TransformationTest
{
    private StreamExecutionEnvironment env;

    @Before
    public void init()
    {
        env = StreamExecutionEnvironment.getExecutionEnvironment();
    }

    @Test
    public void union() throws Exception
    {
        DataStreamSource<Integer> d1 = env.fromElements(1, 2, 3, 4, 5);
        DataStreamSource<Integer> d2 = env.fromElements(66, 77, 614);
        d1.union(d2).print().setParallelism(1);
        env.execute();
    }

    @Test
    public void select() throws Exception
    {
        DataStreamSource<Integer> d1 = env.fromElements(1, 2, 3, 4, 5);
        d1.split(new OutputSelector<Integer>()
        {
            @Override
            public Iterable<String> select(Integer value)
            {
                List<String> list = new ArrayList<>();
                if (value % 2 == 0)
                {
                    list.add("even");
                }
                else
                {
                    list.add("old");
                }
                return list;
            }
        }).select("even").print();
        env.execute();
    }

    @Test
    @SneakyThrows
    public void sink2Mysql()
    {
        DataStreamSource<Student> d = env.addSource(new GenStudent());
        d.addSink(new SinkToMySql());
        env.execute();
    }
}
