package bekyiu.datastream;

import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.sink.RichSinkFunction;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @Author: wangyc
 * @Date: 2020/11/8 11:37
 *
 * 多个线程复用一个对象
 */
public class SinkToMySql extends RichSinkFunction<Student>
{
    public SinkToMySql()
    {
        System.out.println("构造函数");
    }

    static
    {
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
    }
    private Connection getConnection()
    {
        Connection conn = null;
        try
        {
            String url = "jdbc:mysql://localhost:3306/jdbcdemo";
            conn = DriverManager.getConnection(url, "root", "Louise");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return conn;
    }

    // 每个线程初始化的时候都会调用一次
    @Override
    public void open(Configuration parameters) throws Exception
    {
        System.out.println("SinkToMySql.open");
    }

    @Override
    public void invoke(Student value, Context context)
    {
        Connection conn = getConnection();
        PreparedStatement ps = null;
        try
        {
            ps = conn.prepareStatement("insert into student(name, age) values (?, ?)");
            ps.setObject(1, value.getName());
            ps.setObject(2, value.getAge());
            ps.executeUpdate();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                conn.close();
                ps.close();
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void close() throws Exception
    {
        System.out.println("SinkToMySql.close");
    }
}
