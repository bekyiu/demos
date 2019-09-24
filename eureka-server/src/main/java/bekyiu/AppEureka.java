package bekyiu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer // 声明当前spring boot应用是一个eureka服务中心
public class AppEureka
{
    public static void main(String[] args)
    {
        SpringApplication.run(AppEureka.class, args);
    }
}
