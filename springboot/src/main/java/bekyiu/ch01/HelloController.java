package bekyiu.ch01;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
//@EnableAutoConfiguration //开启自动配置
public class HelloController
{

    @RequestMapping("/hello")
    public String hello()
    {
        return "hello SpringBoot";
    }

//    public static void main(String[] args)
//    {
//        SpringApplication.run(HelloController.class, args);
//    }
}
