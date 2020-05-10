package bekyiu.service.impl;

import bekyiu.domain.UserAddress;
import bekyiu.service.IOrderService;
import bekyiu.service.IUserService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements IOrderService
{
    // 相当于 <dubbo:reference id="userService" interface="bekyiu.service.IUserService" />
    @Reference(version = "2.0.0", // 确定使用哪个版本的服务, * 表示随机
            stub = "bekyiu.service.impl.UserServiceStub", // 指定使用的本地存根
            mock = "true") // 服务降级 true表示使用自定义的降级方法, 也可以直接 return null
    private IUserService service;

//    @Reference(version = "1.0.0")
//    private IUserService service;
    @Override
    public List<UserAddress> initOrder(Long userId)
    {
        System.out.println("OrderServiceImpl.initOrder1");
        // 执行本地存根, 相当于执行UserServiceStub.getUserAddrList(userId)
        List<UserAddress> userAddrList = service.getUserAddrList(userId);
        System.out.println("OrderServiceImpl.initOrder2");
        return userAddrList;
    }
}
