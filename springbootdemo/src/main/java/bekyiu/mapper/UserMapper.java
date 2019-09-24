package bekyiu.mapper;

import bekyiu.domain.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper
{
    void save(User user);
    List<User> listAll();
}
