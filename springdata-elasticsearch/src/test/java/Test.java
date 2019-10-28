import bekyiu.ElasticSearchApplication;
import bekyiu.pojo.Item;
import bekyiu.repository.ItemRepository;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest(classes = ElasticSearchApplication.class)
@RunWith(SpringRunner.class)
public class Test
{
    @Autowired
    private ElasticsearchTemplate template;

    @Autowired
    private ItemRepository itemRepository;

    @org.junit.Test
    public void create()
    {
        template.createIndex(Item.class);
        template.putMapping(Item.class);
    }
    @org.junit.Test
    public void curd()
    {
//        Item item = new Item(1L, "小米手机7", " 手机",
//                "小米", 3499.00, "http://image.leyou.com/13123.jpg");
//        itemRepository.save(item);
        List<Item> list = new ArrayList<>();
        list.add(new Item(2L, "坚果手机R1", " 手机", "锤子", 3699.00, "http://image.leyou.com/123.jpg"));
        list.add(new Item(3L, "华为META10", " 手机", "华为", 4499.00, "http://image.leyou.com/3.jpg"));
        // 接收对象集合，实现批量新增
        itemRepository.saveAll(list);
    }

    // 基本查询
    @org.junit.Test
    public void query()
    {
        Iterable<Item> all = itemRepository.findAll(Sort.by("price").descending());
        all.forEach(System.out :: println);
        Optional<Item> item = itemRepository.findById(1L);
        System.out.println(item.get());
        System.out.println("-----");
        // 自定义方法查询
        itemRepository.findByTitle("手机").stream().forEach(System.out :: println);
    }
}
