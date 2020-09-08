package bekyiu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @Author: wangyc
 * @Date: 2020/9/8 14:50
 */
@SpringBootApplication
@EnableDiscoveryClient
public class Gateway
{
    public static void main(String[] args)
    {
        SpringApplication.run(Gateway.class, args);
    }
}
