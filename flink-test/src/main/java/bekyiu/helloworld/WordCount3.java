package bekyiu.helloworld;

import lombok.*;
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
public class WordCount3
{
    public static void main(String[] args) throws Exception
    {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        // nc -l -p 7777
        DataStreamSource<String> dataSource = env.socketTextStream("localhost", 7777);
        dataSource.flatMap(new FlatMapFunction<String, WC>()
        {
            @Override
            public void flatMap(String value, Collector<WC> out) throws Exception
            {
                // 读到的一行
                System.out.println(value);
                Arrays.stream(value.split(",")).forEach(token -> {
                    WC wc = new WC();
                    wc.setWord(token);
                    wc.setCount(1);
                    out.collect(wc);
                });
            }
        }).keyBy("word") // 属性名称
                .timeWindow(Time.seconds(5))
                .sum("count")
                .print()
                .setParallelism(1);

        // 使用流式计算必须要写这个
        env.execute();
    }

    // java bean规范
    @Data
    public static class WC
    {
        private String word;
        private Integer count;
    }
}
