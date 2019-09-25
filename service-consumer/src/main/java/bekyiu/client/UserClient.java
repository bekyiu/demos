package bekyiu.client;

import bekyiu.domain.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

// 声明这是一个feign客户端, 同时value指定使用哪个服务
// fallback指定到哪里去找降级方法
@FeignClient(value = "service-provider", fallback = UserClientFallback.class)
public interface UserClient
{
    // 接口中定义的方法, 完全采用SpringMVC的注解
    // 这些方法的实现, 会通过动态代理生成
    // feign会根据服务的id, controller传递的参数拼接url, 然后通过restTemplate进行远程调用
    @GetMapping("/users/{id}")
    User getUserById(@PathVariable("id") Long id);
}
