package bekyiu.service;

import bekyiu.domain.UserAddress;

import java.util.Arrays;
import java.util.List;

// 编写对应的降级方法, 类名一定是接口名+Mock
public class IUserServiceMock implements IUserService
{
    @Override
    public List<UserAddress> getUserAddrList(Long userId)
    {
        UserAddress userAddress = new UserAddress(0L, "降级", 0L, "");
        return Arrays.asList(userAddress);
    }
}
