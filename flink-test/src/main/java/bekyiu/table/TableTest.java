package bekyiu.table;

import bekyiu.datastream.GenStudent;
import bekyiu.datastream.Student;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.java.BatchTableEnvironment;
import org.apache.flink.table.api.java.StreamTableEnvironment;
import org.junit.Test;

/**
 * @Author: wangyc
 * @Date: 2020/11/8 12:49
 */
public class TableTest
{
    @Test
    public void t1()
    {
//        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
//        StreamTableEnvironment tenv = BatchTableEnvironment.getTableEnvironment(env);
//
//        DataStreamSource<Student> d = env.addSource(new GenStudent());
//
//        Table table = tenv.fromDataStream(d);
//        tenv.registerTable("student", table);
//        Table result = tenv.sqlQuery("select * from student");
//        DataStream<Student> d2 = tenv.toAppendStream(result, Student.class);
//        d2.print();

    }
}
