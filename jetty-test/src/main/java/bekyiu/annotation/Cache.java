package bekyiu.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author: wangyc
 * @Date: 2020/12/27 20:52
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Cache
{
    Class<?> keyClass();
    String keyMethod();
    long ttl() default -1L;
}
