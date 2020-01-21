package bekyiu.service.impl;

import bekyiu.domain.User;
import bekyiu.mapper.UserMapper;
import bekyiu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UserService
{
    @Autowired
    private UserMapper userMapper;

    @Transactional
    @Override
    public void save(User user)
    {
        userMapper.save(user);
    }

    @Transactional
    @Override
    public List<User> list()
    {
        return userMapper.list();
    }
}
