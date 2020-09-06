package bekyiu.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Slf4j
// 优先于事务的切面
@Order(5)
public class LogAspect
{

    // service包及其子包下的所有类的所有方法
    @Around("execution(* bekyiu.service..*.*(..))")
    public Object recordTimeLog(ProceedingJoinPoint joinPoint) throws Throwable
    {
        log.info("===== 开始执行: {}.{} =====", joinPoint.getTarget().getClass(),
                joinPoint.getSignature().getName());

        long start = System.currentTimeMillis();
        Object res = joinPoint.proceed();
        long end = System.currentTimeMillis();
        long gap = end - start;
        if(gap > 5000)
        {
            log.warn("===== 执行结束:{}.{} 执行时间过长: {}毫秒 =====",
                    joinPoint.getTarget().getClass(), joinPoint.getSignature().getName(), gap);
        }
        else
        {
            log.info("===== 执行结束: {}.{} 执行时间: {}毫秒 =====",
                    joinPoint.getTarget().getClass(), joinPoint.getSignature().getName(), gap);
        }
        return res;
    }
}