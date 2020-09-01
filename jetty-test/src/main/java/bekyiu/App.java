package bekyiu;

import bekyiu.bean.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @Author: wangyc
 * @Date: 2020/7/13 21:31
 */
@SpringBootApplication
public class App
{
    public static void main(String[] args)
    {
        ConfigurableApplicationContext ctx = SpringApplication.run(App.class, args);
        User bean = ctx.getBean(User.class);
        System.out.println("获取bean对象");
        System.out.println(bean);
        ctx.close();
    }
}
