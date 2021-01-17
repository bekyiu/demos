package bekyiu.config;

import bekyiu.domain.Person;
import bekyiu.domain.Pet;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: wangyc
 * @Date: 2021/1/17 15:32
 */
// 注入类
//@Import({Pet.class})
// proxyBeanMethods 表示被@bean注解的方法的代理永远开启, 即使是通过代码直接调用
// 这里的代理就是指返回从容器中获取的对象还是直接new
@Configuration(proxyBeanMethods = true)
@EnableConfigurationProperties(value = {Pet.class})
public class BeanConfig
{
    // 当指定bean在容器中存在时
    @ConditionalOnBean(value = {Pet.class})
    @Bean
    public Person person()
    {
        Person p = new Person();
        return p;
    }
}
