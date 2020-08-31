package bekyiu.service;

import bekyiu.annotation.Trans;
import org.springframework.stereotype.Service;

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
        return "{\"age\":\"26\"}";
    }
}
