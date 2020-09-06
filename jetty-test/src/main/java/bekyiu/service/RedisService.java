package bekyiu.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @Author: wangyc
 * @Date: 2020/9/4 14:51
 */
@Service
@Slf4j
public class RedisService
{
    @Autowired
    private StringRedisTemplate redisTemplate;

    private static final String key = "lock:";


    public String lock(String prefix)
    {
        // 获取锁的超时时间
        long acquireTimeout = 5000;
        // 锁的持有时间
        long lockTimeout = 5000;

        String lockKey = key + prefix;
        String value = UUID.randomUUID().toString();

        long endTime = System.currentTimeMillis() + acquireTimeout;

        while (System.currentTimeMillis() < endTime)
        {
            Boolean locked = redisTemplate.opsForValue().setIfAbsent(lockKey, value);
            if (locked != null && locked)
            {
                redisTemplate.expire(lockKey, lockTimeout, TimeUnit.MILLISECONDS);
                log.info("加锁成功 key: {}, value: {}", lockKey, value);
                return value;
            }
            try
            {
                // 随机值，防止所有锁同一时间去抢锁
                log.info("sleep... key: {}", lockKey);
                Thread.sleep(new Random().nextInt(10) + 1);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
        return null;
    }

    public boolean unlock(String prefix, String value)
    {
        String lockKey = key + prefix;
        String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
        RedisScript redisScript = RedisScript.of(script, Long.class);

        Long execute = (Long) redisTemplate.execute(redisScript, Collections.singletonList(lockKey), value);
        if (Long.valueOf(1).equals(execute))
        {
            log.info("解锁成功 key: {}, value: {}", lockKey, value);
            return true;
        }
        log.info("解锁失败 key: {}, value: {}", lockKey, value);
        return false;
    }

}
