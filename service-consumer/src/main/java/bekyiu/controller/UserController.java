package bekyiu.controller;

import bekyiu.domain.User;
import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/consumer/users")
@DefaultProperties(defaultFallback = "defaultFallBack")
public class UserController
{
//    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private DiscoveryClient discoveryClient;

    @GetMapping("{id}")
    // 当此方法无法正常返回的时候 调用降级方法
    @HystrixCommand(fallbackMethod = "getUserByIdFallBack")
    public User getUserById(@PathVariable("id") Long id)
    {
        if(id == 1L)
        {
            throw new RuntimeException();
        }
        // service-provider 是服务的name
        String url = "http://service-provider/users/" + id;
        return restTemplate.getForObject(url, User.class);
    }
    // 降级方法, 必须与正常的方法返回值和参数列表相同
    // 当请求失败 被拒绝 超时或者断路器打开时, 都会进入回退方法 但进入
    // 回退方法并不意味着断路器已经被打开
    public User getUserByIdFallBack(Long id)
    {
        User user = new User();
        user.setId(-1L);
        user.setName("出现错误");
        return user;
    }


    // 默认的降级方法
    public User defaultFallBack()
    {
        User user = new User();
        user.setId(-1L);
        user.setName("defaultFallBack");
        return user;
    }

    @GetMapping("/sb/{id}")
    @HystrixCommand
    public User getUserById1(@PathVariable("id") Long id)
    {
        // 获取所有在eureka中注册的服务的id
        System.out.println(discoveryClient.getServices());
        // 根据id获取指定的服务, 因为指定的服务可能会有集群, 所以返回的是list
        List<ServiceInstance> instances = discoveryClient.getInstances("service-provider");
        // 一个服务实例就是一台机器
        ServiceInstance instance = instances.get(0);
        return restTemplate.getForObject(instance.getUri() + "/users/" + id, User.class);
    }
}
