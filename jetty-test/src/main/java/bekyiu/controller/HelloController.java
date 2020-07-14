package bekyiu.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: wangyc
 * @Date: 2020/7/13 21:32
 */
@RestController
public class HelloController
{
    @GetMapping("/ha")
    public String ha(String id)
    {
        System.out.println("HelloController.ha");
        return "ha" + id;
    }
}
