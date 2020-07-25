package bekyiu.ch02.mapper;

import bekyiu.ch02.domain.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

//@Mapper
public interface UserMapper
{
    void save(User user);
    List<User> listAll();
}
