package bekyiu.helloworld;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.operators.DataSource;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.util.Collector;

import java.util.Arrays;

/**
 * @Author: wangyc
 * @Date: 2020/10/31 12:24
 *
 * 批处理程序
 * 统计词频
 */
public class WordCount
{
    public static void main(String[] args) throws Exception
    {
        String path = "file:///IDEAProjects/demos/flink-test/hello.txt";
        // 获取运行环境
        ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();
        // 从文件读数据
        DataSource<String> dataSource = env.readTextFile(path);
        // 处理 类似java8的stream
        dataSource.flatMap(new FlatMapFunction<String, Tuple2<String, Integer>>()
        {
            @Override
            public void flatMap(String value, Collector<Tuple2<String, Integer>> out) throws Exception
            {
                // 读到的一行
                System.out.println(value);
                Arrays.stream(value.split(" ")).forEach(token -> {
                    out.collect(new Tuple2<>(token, 1));
                });
            }
        }).groupBy(0).sum(1).print();
    }
}
