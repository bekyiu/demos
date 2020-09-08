package bekyiu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @Author: wangyc
 * @Date: 2020/9/8 10:47
 */
@SpringBootApplication
@EnableDiscoveryClient
public class ProviderApp
{
    public static void main(String[] args)
    {
        SpringApplication.run(ProviderApp.class, args);
    }
}
