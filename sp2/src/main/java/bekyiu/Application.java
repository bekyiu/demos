package bekyiu;

import bekyiu.domain.Person;
import bekyiu.domain.Pet;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @Author: wangyc
 * @Date: 2021/1/17 15:28
 */
@SpringBootApplication
public class Application
{

    public static void main(String[] args)
    {
        ConfigurableApplicationContext ctx = SpringApplication.run(Application.class, args);
        // 打印容器中bean的名字
//        String[] names = ctx.getBeanDefinitionNames();
//        for (String name : names)
//        {
//            System.out.println(name);
//        }

        Pet pet = ctx.getBean(Pet.class);
        System.out.println(pet);
    }
}
