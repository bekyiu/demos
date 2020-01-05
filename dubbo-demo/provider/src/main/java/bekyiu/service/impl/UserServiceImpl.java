package bekyiu.service.impl;

import bekyiu.domain.UserAddress;
import bekyiu.service.IUserService;

import java.util.Arrays;
import java.util.List;

public class UserServiceImpl implements IUserService
{
    @Override
    public List<UserAddress> getUserAddrList(Long userId)
    {
        return Arrays.asList(
                new UserAddress(1L, "四川成都", 614L, "110"),
                new UserAddress(2L, "火星", 123L, "119"),
                new UserAddress(3L, "美国", 666L, "120")
        );
    }
}
