package bekyiu.datastream;

import org.apache.flink.streaming.api.functions.source.RichParallelSourceFunction;
import org.apache.flink.streaming.api.functions.source.SourceFunction;

/**
 * @Author: wangyc
 * @Date: 2020/11/8 11:45
 */
public class GenStudent implements SourceFunction<Student>
{
    @Override
    public void run(SourceContext<Student> ctx) throws Exception
    {
        for (int i = 0; i < 10; i++)
        {
            Student s = new Student();
            s.setId(7L);
            s.setAge(i);
            s.setName("name" + i);
            ctx.collect(s);
        }
    }

    @Override
    public void cancel()
    {
        System.out.println("GenStudent.cancel");
    }
}
