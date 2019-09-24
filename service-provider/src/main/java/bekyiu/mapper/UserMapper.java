package bekyiu.mapper;

import bekyiu.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper
{
    User get(@Param("id") Long id);
}
