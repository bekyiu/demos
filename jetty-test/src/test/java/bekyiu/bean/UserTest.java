package bekyiu.bean;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Author: wangyc
 * @Date: 2020/9/1 10:59
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class UserTest
{
    @Autowired
    private ConfigurableApplicationContext ctx;
    @Test
    public void test()
    {
        User bean = ctx.getBean(User.class);
        System.out.println("获取bean对象");
        System.out.println(bean);
        ctx.close();
    }
}