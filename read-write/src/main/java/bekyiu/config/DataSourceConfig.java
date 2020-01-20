package bekyiu.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * 要做读写分离, 至少要有两个数据库
 * master用于增删改, slave用于查询
 * 一个datasource只能连接一个数据库, 现在想要连接两个数据库, 所以需要两个datasource
 * 除此之外, 还需要另外一个东西, 来对要执行的操作进行路由
 * 比如: save(xxx) 到master执行, list() 则到slave执行
 * 所以这里再配置一个routingDatasource, 用于路由, 和管理其他的连接池
 */
@Configuration
public class DataSourceConfig
{
    @Bean
    public DataSource master()
    {
        DruidDataSource master = new DruidDataSource();
        master.setUrl("jdbc:mysql://localhost:3306/new_db");
        master.setUsername("root");
        master.setPassword("Louise");
        return master;
    }

    @Bean
    public DataSource slave()
    {
        DruidDataSource slave = new DruidDataSource();
        slave.setUrl("jdbc:mysql://localhost:3307/new_db");
        slave.setUsername("root");
        slave.setPassword("Louise");
        return slave;
    }

    @Bean
    public DataSource routingDataSource(DataSource master, DataSource slave)
    {
        Map<Object, Object> map = new HashMap<>();
        map.put("master", master);
        map.put("slave", slave);
        RoutingDataSource dataSource = new RoutingDataSource();
        // 设置要被管理的连接池
        dataSource.setTargetDataSources(map);
        // 设置默认选择的连接池
        dataSource.setDefaultTargetDataSource(master);
        return dataSource;
    }
}
