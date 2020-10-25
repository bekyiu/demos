package bekyiu.dao;

import bekyiu.domain.Comment;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @Author: wangyc
 * @Date: 2020/10/25 21:21
 * 泛型: 操作的实体, 主键
 */
@Repository
public interface CommentDao extends MongoRepository<Comment, String>
{
}
