package bekyiu.service;

import bekyiu.domain.User;

import java.util.List;

public interface UserService
{
    void save(User user);

    List<User> list();
}
