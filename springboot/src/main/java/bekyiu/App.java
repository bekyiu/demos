package bekyiu;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@EnableAutoConfiguration // 开启自动配置
//@ComponentScan // ioc注解解析器 扫描该类所在的包以及子包
@SpringBootApplication // 该注解是以上注解的组合
@MapperScan("bekyiu.ch02.mapper")
public class App
{
    public static void main(String[] args)
    {
        SpringApplication.run(App.class, args);
    }
}
