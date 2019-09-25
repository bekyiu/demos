package bekyiu.client;

import bekyiu.domain.User;
import org.springframework.stereotype.Component;

// 此类用来编写降级方法
@Component
public class UserClientFallback implements UserClient
{
    @Override
    public User getUserById(Long id)
    {
        User user = new User();
        user.setName("UserClientFallback中的降级方法");
        return user;
    }
}
