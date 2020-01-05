package bekyiu.service.impl;

import bekyiu.domain.UserAddress;
import bekyiu.service.IUserService;

import java.util.List;

// 本地存根
public class UserServiceStub implements IUserService
{
    private final IUserService userService;
    public UserServiceStub(IUserService userService)
    {
        this.userService = userService;
    }

    @Override
    public List<UserAddress> getUserAddrList(Long userId)
    {
        // 验证, 判断...
        System.out.println("进入本地存根 UserServiceStub.getUserAddrList");
        return userService.getUserAddrList(userId);
    }
}
