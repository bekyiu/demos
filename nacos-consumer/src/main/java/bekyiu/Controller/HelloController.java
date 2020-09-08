package bekyiu.Controller;

import bekyiu.client.HelloApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @GetMapping("/z")
    public String hello()
    {
        System.out.println("consumer...");
        return "hello" + helloApi.hello();
    }
}
