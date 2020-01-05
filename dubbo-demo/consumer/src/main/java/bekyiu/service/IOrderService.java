package bekyiu.service;

import bekyiu.domain.UserAddress;

import java.util.List;

public interface IOrderService
{
    /**
     * 根据userId 初始化订单
     * @param userId
     */
    List<UserAddress> initOrder(Long userId);
}
