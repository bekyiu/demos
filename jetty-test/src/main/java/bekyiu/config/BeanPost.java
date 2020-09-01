package bekyiu.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 * @Author: wangyc
 * @Date: 2020/9/1 11:28
 */
@Component
public class BeanPost implements BeanPostProcessor
{
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException
    {
        if (beanName.equals("user"))
        {
            System.out.println("BeanPost.postProcessBeforeInitialization");
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException
    {
        if (beanName.equals("user"))
        {
            System.out.println("BeanPost.postProcessAfterInitialization");
        }
        return bean;
    }
}
