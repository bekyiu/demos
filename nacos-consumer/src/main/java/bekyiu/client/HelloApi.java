package bekyiu.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @Author: wangyc
 * @Date: 2020/9/8 11:01
 */
@FeignClient("nacos-provider")
public interface HelloApi
{
    @GetMapping("/hello")
    String hello();
}
