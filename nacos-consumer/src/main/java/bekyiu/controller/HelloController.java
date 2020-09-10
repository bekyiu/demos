package bekyiu.controller;

import bekyiu.client.HelloApi;
import bekyiu.service.TeaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: wangyc
 * @Date: 2020/9/8 10:58
 */
@RestController
public class HelloController
{

    @Autowired
    private HelloApi helloApi;

    @Autowired
    private TeaService teaService;

    @GetMapping("/z")
    public String hello()
    {
        System.out.println("consumer...");
        return "hello" + helloApi.hello();
    }

    @GetMapping("/bigSave")
    public String bigSave()
    {
        teaService.saveTea();
        return "hah";
    }
}
