package bekyiu.bean;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * @Author: wangyc
 * @Date: 2020/9/1 10:54
 *
 * 测试bean的生命周期
 */
@Component
public class User implements InitializingBean, DisposableBean
{
    private String name;
    public User()
    {
        System.out.println("User.User");
    }
    @Value("nanase")
    public void setName(String name)
    {
        System.out.println("User.setName");
        this.name = name;
    }

    @PostConstruct
    public void initMethod()
    {
        System.out.println("User.initMethod");
    }

    @PreDestroy
    public void destroyMethod()
    {
        System.out.println("User.destroyMethod");
    }

    @Override
    public void afterPropertiesSet() throws Exception
    {
        System.out.println("User.afterPropertiesSet");
    }

    @Override
    public void destroy() throws Exception
    {
        System.out.println("User.destroy");
    }
}
