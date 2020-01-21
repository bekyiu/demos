package bekyiu.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@MapperScan("bekyiu.mapper")
@EnableTransactionManagement
public class MyBatisConfig
{
    @Autowired
    private DataSource routingDataSource;

    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception
    {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        // 具有路由功能的连接池
        bean.setDataSource(routingDataSource);
        bean.setTypeAliasesPackage("bekyiu.domain");
        return bean.getObject();
    }

    @Bean
    public PlatformTransactionManager transactionManager()
    {
        return new DataSourceTransactionManager(routingDataSource);
    }
}
