package bekyiu.repository;

import bekyiu.pojo.Item;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface ItemRepository extends ElasticsearchRepository<Item, Long>
{
    // 按照规则编写方法名即可
    List<Item> findByTitle(String title);
}
