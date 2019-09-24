package bekyiu.controller;

import bekyiu.domain.User;
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
public class UserController
{
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private DiscoveryClient discoveryClient;

    @GetMapping("{id}")
    public User getUserById(@PathVariable("id") Long id)
    {
        // service-provider 是服务的name
        String url = "http://service-provider/users/" + id;
        return restTemplate.getForObject(url, User.class);
    }

    @GetMapping("/sb/{id}")
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
