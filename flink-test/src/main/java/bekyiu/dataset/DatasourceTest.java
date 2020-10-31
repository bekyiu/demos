package bekyiu.dataset;

import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.configuration.Configuration;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 * @Author: wangyc
 * @Date: 2020/10/31 20:19
 */
public class DatasourceTest
{
    private ExecutionEnvironment env;
    @Before
    public void init()
    {
        env = ExecutionEnvironment.getExecutionEnvironment();
    }

    @Test
    public void f1() throws Exception
    {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);
        env.fromCollection(list).print();
    }

    @Test
    public void f2() throws Exception
    {
        String path = "file:///IDEAProjects/demos/flink-test";
        env.readTextFile(path).print();
    }

    @Test
    public void f3() throws Exception
    {
        String path = "file:///IDEAProjects/demos/flink-test/src/main/java/bekyiu/helloworld";
        Configuration config = new Configuration();
        config.setBoolean("recursive.file,enumeration", true);
        env.readTextFile(path).withParameters(config).print();
    }
}
