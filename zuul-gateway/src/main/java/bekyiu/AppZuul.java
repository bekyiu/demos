package bekyiu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@SpringBootApplication
@EnableZuulProxy // 开启zuul网关
@EnableDiscoveryClient
public class AppZuul
{
    public static void main(String[] args)
    {
        SpringApplication.run(AppZuul.class, args);
    }
}
