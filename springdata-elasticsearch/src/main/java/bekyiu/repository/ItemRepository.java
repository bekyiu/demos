package bekyiu.repository;

import bekyiu.pojo.Item;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

// 第一个泛型是实体的类型, 第二个泛型是主键的类型
public interface ItemRepository extends ElasticsearchRepository<Item, Long>
{
    // 按照规则编写方法名即可
    List<Item> findByTitle(String title);
}
