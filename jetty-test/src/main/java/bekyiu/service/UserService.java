package bekyiu.service;

import bekyiu.annotation.Cache;
import bekyiu.annotation.Trans;
import bekyiu.domain.User;
import bekyiu.util.RedisKey;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @Author: wangyc
 * @Date: 2020/8/31 15:59
 */
@Service
public class UserService
{
    @Trans("nana")
    public String user()
    {
//        int a = 1 / 0;
        int[] a = {1};
        a[1] = 7;
        return "{\"age\":\"26\"}";
    }

    @Cache(keyClass = RedisKey.class, keyMethod = "userKey")
    public User get(Map<String, Object> param) {
        System.out.println("UserService.get");
        Long id = (Long) param.get("id");
        return User.builder().id(id).username("nanase").build();
    }
}
