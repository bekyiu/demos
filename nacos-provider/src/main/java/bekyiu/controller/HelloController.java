package bekyiu.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: wangyc
 * @Date: 2020/9/8 10:58
 */
@RestController
// 配置改变后自动刷新
@RefreshScope
public class HelloController
{
    @Value("${name}")
    private String name;

    @GetMapping("/hello")
    public String hello()
    {
        System.out.println("provider...");
        return name;
    }
}
