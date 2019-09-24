package bekyiu.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

// Springboot 推荐的java config方式
@Configuration
public class JdbcConfiguration
{
//    @Bean
//    @ConfigurationProperties(prefix = "jdbc")
//    // 在application.properties中读取前缀为jdbc的值
//    // 然后通过当前bean的setter注入
//    public DataSource dataSource()
//    {
//        return new DruidDataSource();
//    }
}
