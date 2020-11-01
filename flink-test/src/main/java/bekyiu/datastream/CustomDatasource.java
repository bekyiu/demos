package bekyiu.datastream;

import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.source.ParallelSourceFunction;
import org.apache.flink.streaming.api.functions.source.SourceFunction;
import org.junit.Before;
import org.junit.Test;

/**
 * @Author: wangyc
 * @Date: 2020/11/1 19:57
 */
public class CustomDatasource
{
    private StreamExecutionEnvironment env;

    @Before
    public void init()
    {
        env = StreamExecutionEnvironment.getExecutionEnvironment();
    }
    @Test
    public void t1() throws Exception
    {
        // 自定义数据源, 不能并行
        DataStreamSource<Long> ds = env.addSource(new SourceFunction<Long>()
        {
            private boolean isRunning = true;
            private long count = 0;

            @Override
            public void run(SourceContext<Long> ctx) throws Exception
            {
                while (isRunning)
                {
                    ctx.collect(++count);
                    Thread.sleep(1000);
                }
            }

            @Override
            public void cancel()
            {
                isRunning = false;
            }
        });

        ds.print();

        env.execute();
    }
    @Test
    public void t2() throws Exception
    {
        // 自定义数据源, 并行
        // RichParallelSourceFunction
        DataStreamSource<Long> ds = env.addSource(new ParallelSourceFunction<Long>()
        {
            private boolean isRunning = true;
            private long count = 0;

            @Override
            public void run(SourceContext<Long> ctx) throws Exception
            {
                while (isRunning)
                {
                    ctx.collect(++count);
                    Thread.sleep(1000);
                }
            }

            @Override
            public void cancel()
            {
                isRunning = false;
            }
        });

        ds.print();

        env.execute();
    }
}
