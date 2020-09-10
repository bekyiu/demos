package bekyiu;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @Author: wangyc
 * @Date: 2020/9/8 10:47
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@MapperScan("bekyiu.mapper")
public class ConsumerApp
{
    public static void main(String[] args)
    {
        SpringApplication.run(ConsumerApp.class, args);
    }
}
