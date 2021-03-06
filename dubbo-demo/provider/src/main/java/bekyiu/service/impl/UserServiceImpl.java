package bekyiu.service.impl;

import bekyiu.domain.UserAddress;
import bekyiu.service.IUserService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Service(version = "1.0.0") // 注意这是dubbo的注解而不是spring的, 用于暴露服务的接口
// 相当于 <dubbo:service interface="bekyiu.service.IUserService" ref="userService" />
@Component
public class UserServiceImpl implements IUserService
{
    @Override
    public List<UserAddress> getUserAddrList(Long userId)
    {
        System.out.println("old");
        return Arrays.asList(
                new UserAddress(1L, "四川成都", 614L, "110"),
                new UserAddress(2L, "火星", 123L, "119"),
                new UserAddress(3L, "美国", 666L, "120")
        );
    }
}
