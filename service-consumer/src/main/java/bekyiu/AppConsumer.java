package bekyiu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableDiscoveryClient
@EnableCircuitBreaker // 开启熔断
// @SpringCloudApplication 以上三个注解可以用此注解代替
public class AppConsumer
{
    public static void main(String[] args)
    {
        SpringApplication.run(AppConsumer.class, args);
    }

    @Bean
    @LoadBalanced //开启负载均衡
    public RestTemplate restTemplate()
    {
        return new RestTemplate();
    }
}
