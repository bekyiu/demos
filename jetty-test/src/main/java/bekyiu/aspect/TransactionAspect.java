package bekyiu.aspect;

import bekyiu.annotation.Trans;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @Author: wangyc
 * @Date: 2020/8/31 15:20
 */
@Component
@Aspect
@Slf4j
@Order(10)
public class TransactionAspect
{
    // 切面切在注解上
    @Pointcut("@annotation(bekyiu.annotation.Trans)")
    public void point() {}

    @Around("point()")
    public Object around(ProceedingJoinPoint jp) throws Throwable
    {
        Object res = null;
        try
        {
            getAnnotationValue(jp);
            log.info("开启事务");
            res = jp.proceed();
            log.info("提交事务");
        }
        catch (Exception e)
        {
            log.info("回滚");
            // 交给统一异常处理
            throw e;
        }
        finally
        {
            log.info("释放资源");
        }
        return res;
    }

    private void getAnnotationValue(ProceedingJoinPoint jp) throws NoSuchMethodException
    {
        Class<?> clzz = jp.getTarget().getClass();
        MethodSignature signature = (MethodSignature) jp.getSignature();
        Method method = clzz.getDeclaredMethod(signature.getName(), signature.getParameterTypes());
        Trans trans = method.getAnnotation(Trans.class);
        log.info("获取到注解的value: {}", trans.value());
    }
}
