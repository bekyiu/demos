package bekyiu.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

/**
 * @Author: wangyc
 * @Date: 2020/9/4 16:18
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class RedisServiceTest
{
    @Autowired
    private RedisService lock;

    @Test
    public void t()
    {

        String prefix = "order";
        new Thread(() ->
        {
            String value = lock.lock(prefix);
            lock.unlock(prefix, value);
        }).start();

        new Thread(() ->
        {
            String value = lock.lock(prefix);
            lock.unlock(prefix, value);
        }).start();

        new Thread(() ->
        {
            String value = lock.lock(prefix);
            lock.unlock(prefix, value);
        }).start();

        try
        {
            TimeUnit.SECONDS.sleep(5);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }
}