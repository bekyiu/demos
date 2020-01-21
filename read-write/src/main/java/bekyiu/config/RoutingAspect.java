package bekyiu.config;

import bekyiu.utils.RoutingUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 在执行service方法之前, 先确定使用哪个连接池
 * 该切面应当在事务切面之前执行, 如果先执行事务切面的话, 会先设置好routingDataSource
 * 然后determineCurrentLookupKey()方法会执行, 此时get到的key为null
 */
@Component
@Aspect
@Order(0) // 让该切面先于事务的切面之前执行, 默认是int的最大值, 数字越小优先级越高
public class RoutingAspect
{
    @Pointcut("execution(* bekyiu.service.impl.*Impl.*(..))")
    public void pc()
    {
    }

    @Before("pc()")
    public void setRoutingOption(JoinPoint point)
    {
        // 当前切入方法的名称
        String methodName = point.getSignature().getName();
        if(isSlave(methodName))
        {
            RoutingUtil.setSlave();
        }
        else
        {
            RoutingUtil.setMaster();
        }
    }

    private boolean isSlave(String methodName)
    {
        return methodName.startsWith("get") ||
                methodName.startsWith("query") ||
                methodName.startsWith("list") ||
                methodName.startsWith("select");
    }
}
