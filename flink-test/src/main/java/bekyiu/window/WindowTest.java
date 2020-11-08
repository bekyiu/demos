package bekyiu.window;

import bekyiu.datastream.GenStudent;
import bekyiu.datastream.Student;
import org.apache.flink.api.common.JobExecutionResult;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.functions.ReduceFunction;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.api.java.operators.DataSource;
import org.apache.flink.api.java.tuple.Tuple;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStreamSink;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.windowing.ProcessAllWindowFunction;
import org.apache.flink.streaming.api.functions.windowing.ProcessWindowFunction;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.util.Collector;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Author: wangyc
 * @Date: 2020/11/8 17:18
 */
public class WindowTest
{
    @Test
    public void t1() throws Exception
    {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        DataStreamSource<Student> d = env.addSource(new GenStudent());
        d.keyBy("id").timeWindow(Time.seconds(10))
                .reduce(new ReduceFunction<Student>()
                {
                    @Override
                    public Student reduce(Student value1, Student value2) throws Exception
                    {
                        System.out.println(value1 + ", " + value2);
                        Student s = new Student();
                        s.setId(value1.getId() + value2.getId());
                        return s;
                    }
                }).print().setParallelism(1);
        // 使用流式计算必须要写这个
        env.execute();
    }

    @Test
    public void t2() throws Exception
    {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        // nc -l -p 7777
        DataStreamSource<String> dataSource = env.socketTextStream("localhost", 7777);
        dataSource.flatMap(new FlatMapFunction<String, Tuple2<String, Integer>>()
        {
            @Override
            public void flatMap(String value, Collector<Tuple2<String, Integer>> out) throws Exception
            {
                Arrays.stream(value.split(",")).forEach(token -> {
                    // Tuple2<String, Integer> 是写出的类型
                    out.collect(new Tuple2<>(token, 1));
                });
            }
        }).keyBy(0)
                // 一个10s的窗口, 累计10s统一处理
                .timeWindow(Time.seconds(5))
                .process(new ProcessWindowFunction<Tuple2<String, Integer>, Object, Tuple, TimeWindow>() {
                    @Override
                    public void process(Tuple tuple, Context context, Iterable<Tuple2<String, Integer>> elements, Collector<Object> out) throws Exception
                    {
                        System.out.println("---");
                        int len = 0;
                        for (Tuple2<String, Integer> element : elements)
                        {
                            len++;
                        }
                        System.out.println(tuple + ": " + len + "window: " + context.window());
                    }
                }).print().setParallelism(1);

        env.execute();
    }
}
