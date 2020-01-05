package bekyiu.service.impl;

import bekyiu.domain.UserAddress;
import bekyiu.service.IOrderService;
import bekyiu.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements IOrderService
{
    @Autowired
    private IUserService service;
    @Override
    public void initOrder(Long userId)
    {
        // 1. 查询用户的收货地址
        List<UserAddress> userAddrList = service.getUserAddrList(userId);
        System.out.println(userAddrList);
    }
}
