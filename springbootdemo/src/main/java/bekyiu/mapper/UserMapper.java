package bekyiu.mapper;

import bekyiu.domain.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper // 告诉Spring要为其创建代理对象
public interface UserMapper
{
    void save(User user);
    List<User> listAll();
}
