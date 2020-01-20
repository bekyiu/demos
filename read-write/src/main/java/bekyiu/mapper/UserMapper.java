package bekyiu.mapper;

import bekyiu.domain.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper
{
    @Insert("insert into user(name, password) values (#{name}, #{password})")
    void save(User user);

    @Select("select * from user")
    List<User> list();
}
