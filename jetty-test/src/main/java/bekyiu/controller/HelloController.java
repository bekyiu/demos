package bekyiu.controller;

import bekyiu.service.UserService;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: wangyc
 * @Date: 2020/7/13 21:32
 */
@RestController
public class HelloController
{
    @Autowired
    private UserService userService;

    @GetMapping("/ha")
    public String ha()
    {
        System.out.println("HelloController.ha");
        return userService.user();
    }
}
