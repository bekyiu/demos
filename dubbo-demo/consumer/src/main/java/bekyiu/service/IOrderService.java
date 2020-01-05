package bekyiu.service;

public interface IOrderService
{
    /**
     * 根据userId 初始化订单
     * @param userId
     */
    void initOrder(Long userId);
}
