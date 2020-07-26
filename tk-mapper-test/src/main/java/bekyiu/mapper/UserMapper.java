package bekyiu.mapper;

import bekyiu.domain.User;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @Author: wangyc
 * @Date: 2020/7/25 13:32
 */
public interface UserMapper extends Mapper<User>
{
    List<User> xmlList();

    void xmlInsert(User u);
}
