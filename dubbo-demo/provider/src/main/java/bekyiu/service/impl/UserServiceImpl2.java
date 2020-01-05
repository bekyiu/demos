package bekyiu.service.impl;

import bekyiu.domain.UserAddress;
import bekyiu.service.IUserService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Service(version = "2.0.0") // 灰度发布
@Component
public class UserServiceImpl2 implements IUserService
{
    @Override
    public List<UserAddress> getUserAddrList(Long userId)
    {
        System.out.println("new");
        return Arrays.asList(
                new UserAddress(1L, "四川成都", 614L, "110"),
                new UserAddress(2L, "火星", 123L, "119"),
                new UserAddress(3L, "美国", 666L, "120")
        );
    }
}
