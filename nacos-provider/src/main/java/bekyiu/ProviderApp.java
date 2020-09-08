package bekyiu;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @Author: wangyc
 * @Date: 2020/9/8 10:47
 */
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("bekyiu.mapper")
public class ProviderApp
{
    public static void main(String[] args)
    {
        SpringApplication.run(ProviderApp.class, args);
    }
}
