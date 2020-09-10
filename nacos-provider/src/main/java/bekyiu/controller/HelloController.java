package bekyiu.controller;

import bekyiu.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private StudentService service;

    @GetMapping("/hello")
    public String hello()
    {
        return name;
    }

    @GetMapping("/saveStu")
    public String save()
    {
        service.save();
        return "ok";
    }
}
