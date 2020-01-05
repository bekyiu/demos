package bekyiu.service;

import bekyiu.domain.UserAddress;

import java.util.List;

public interface IUserService
{
    List<UserAddress> getUserAddrList(Long userId);
}
