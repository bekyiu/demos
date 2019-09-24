package bekyiu.service;

import bekyiu.domain.User;
import bekyiu.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

// spring boot 会自动配置一个 DataSourceTransactionManager (配置类: DataSourceTransactionManagerAutoConfiguration)
// 所以直接贴注解就可以了
@Service
@Transactional
public class UserService
{
    @Autowired
    private UserMapper mapper;

    public void save(User user)
    {
        mapper.save(user);
    }

    @Transactional(readOnly = true)
    public List<User> listAll()
    {
        return mapper.listAll();
    }
}
