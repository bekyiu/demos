package bekyiu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableDiscoveryClient
@EnableCircuitBreaker // 开启熔断
// @SpringCloudApplication 以上三个注解可以用此注解代替
@EnableFeignClients // 开启feign客户端
public class AppConsumer
{
    public static void main(String[] args)
    {
        SpringApplication.run(AppConsumer.class, args);
    }

    // 集成了feign之后, 就不需要自己写RestTemplate了
    // feign已经自动集成了Ribbon负载均衡的RestTemplate
    @Bean
    @LoadBalanced //开启负载均衡
    public RestTemplate restTemplate()
    {
        return new RestTemplate();
    }
}
