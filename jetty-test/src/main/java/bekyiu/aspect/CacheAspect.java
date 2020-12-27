package bekyiu.aspect;

import bekyiu.annotation.Cache;
import bekyiu.annotation.Trans;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Author: wangyc
 * @Date: 2020/12/27 20:55
 */
@Component
@Aspect
@Slf4j
@Order(-10)
public class CacheAspect
{
    @Pointcut("@annotation(bekyiu.annotation.Cache)")
    public void point()
    {
    }

    @Autowired
    StringRedisTemplate redisTemplate;

    @Around("point()")
    public Object around(ProceedingJoinPoint jp) throws Throwable
    {
        Method method = getProcessMethod(jp);
        Cache anno = method.getAnnotation(Cache.class);
        String key = genKey(anno, (Map<String, Object>) jp.getArgs()[0]);
        String json = redisTemplate.opsForValue().get(key);
        if (json != null) {
            return JSON.parseObject(json, method.getReturnType());
        }
        Object res = jp.proceed();
        long ttl = anno.ttl();
        if (ttl > -1) {
            redisTemplate.opsForValue().set(key, JSON.toJSONString(res, SerializerFeature.WriteMapNullValue),
                    ttl, TimeUnit.SECONDS);
        } else {
            redisTemplate.opsForValue().set(key, JSON.toJSONString(res, SerializerFeature.WriteMapNullValue));
        }

        return res;
    }

    private Method getProcessMethod(ProceedingJoinPoint jp) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException
    {
        Class<?> clzz = jp.getTarget().getClass();
        MethodSignature signature = (MethodSignature) jp.getSignature();
        Method method = clzz.getDeclaredMethod(signature.getName(), signature.getParameterTypes());
        return method;
    }

    private String genKey(Cache cacheAnno, Map<String, Object> param) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException
    {
        Class<?> redisKeyClass = cacheAnno.keyClass();
        String keyMethod = cacheAnno.keyMethod();
        Method m = redisKeyClass.getDeclaredMethod(keyMethod, Map.class);
        m.setAccessible(true);
        return (String) m.invoke(null, param);
    }
}
