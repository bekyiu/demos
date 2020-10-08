package bekyiu.many2many.dao;

import bekyiu.many2many.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @Author: wangyc
 * @Date: 2020/10/7 11:18
 *
 * JpaRepository<要操作的实体类类型, 实体类中主键的类型>
 * JpaSpecificationExecutor<要操作的实体类类型>
 */
public interface UserDao extends JpaRepository<User, Long>, JpaSpecificationExecutor<User>
{
}
