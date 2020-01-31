package bekyiu.controller;

import bekyiu.domain.User;
import bekyiu.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/users")
public class UserController
{
    @Autowired
    private IUserService userService;

    @GetMapping("{id}")
    public User getUserById(@PathVariable("id") Long id)
    {
        // 默认情况下: 使用feign进行远程调用, 如果provider中的方法在1s内没有返回, 则会超时报错
        // 并且尝试二次调用服务方法, 如果开启hystrix就不存在这个问题了
        // 超过feign的超时时间但是没有超过hystrix的超时时间, 还是会进入降级方法
        try
        {
            Thread.sleep(500);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        System.out.println("sb");
        return userService.getUserById(id);
    }
}
