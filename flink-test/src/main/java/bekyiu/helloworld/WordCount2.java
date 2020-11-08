package bekyiu.helloworld;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.util.Collector;

import java.util.Arrays;

/**
 * @Author: wangyc
 * @Date: 2020/10/31 15:05
 *
 * 实时处理
 */
public class WordCount2
{
    public static void main(String[] args) throws Exception
    {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        // nc -l -p 7777
        DataStreamSource<String> dataSource = env.socketTextStream("localhost", 7777);
        dataSource.flatMap(new FlatMapFunction<String, Tuple2<String, Integer>>()
        {
            @Override
            public void flatMap(String value, Collector<Tuple2<String, Integer>> out) throws Exception
            {
                // 读到的一行
                System.out.println(value);
                Arrays.stream(value.split(",")).forEach(token -> {
                    // Tuple2<String, Integer> 是写出的类型
                    out.collect(new Tuple2<>(token, 1));
                });
            }
        }).keyBy(0)
                // 一个10s的窗口, 累计10s统一处理
                .timeWindow(Time.seconds(100)).sum(1).print().setParallelism(1);

        // 使用流式计算必须要写这个
        env.execute();
    }
}
