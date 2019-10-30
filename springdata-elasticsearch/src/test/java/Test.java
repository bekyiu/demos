import bekyiu.ElasticSearchApplication;
import bekyiu.pojo.Item;
import bekyiu.repository.ItemRepository;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.elasticsearch.search.aggregations.metrics.avg.InternalAvg;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.FetchSourceFilter;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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

    @org.junit.Test
    public void query2()
    {
        // 构建查询条件
        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
        // 添加基本的查询条件
        nativeSearchQueryBuilder.withQuery(QueryBuilders.matchQuery("title", "小米手机"));
        // 获取结果, 这里没有设置分页, 默认就是分一页
        Page<Item> items = itemRepository.search(nativeSearchQueryBuilder.build());
        // 打印相关信息
        List<Item> content = items.getContent();
        content.forEach(System.out :: println);
        System.out.println(items.getTotalElements());
        System.out.println(items.getTotalPages());
    }

    @org.junit.Test
    public void q()
    {
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        queryBuilder.withQuery(QueryBuilders.termQuery("category", "手机"));
        itemRepository.search(queryBuilder.build()).forEach(System.out :: println);
    }

    @org.junit.Test
    public void page()
    {
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        // 注意手机前面有个空格
        queryBuilder.withQuery(QueryBuilders.termQuery("category", " 手机"));

        // 分页参数
        int page = 1; // 当前页是第几页, 从0开始
        int size = 2; // 每页多少条数据
        queryBuilder.withPageable(PageRequest.of(page, size));
        // 查询
        Page<Item> search = itemRepository.search(queryBuilder.build());
        System.out.println(search.getTotalElements());
        System.out.println(search.getTotalPages());
        System.out.println(search.getSize());
        // 当前页
        System.out.println(search.getNumber());
        search.forEach(System.out :: println);
    }

    @org.junit.Test
    public void sort()
    {
        // 构建查询条件
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        // 添加基本的分词查询
        queryBuilder.withQuery(QueryBuilders.termQuery("category", " 手机"));
        // 添加排序条件
        queryBuilder.withSort(SortBuilders.fieldSort("price").order(SortOrder.ASC));
        Page<Item> items = itemRepository.search(queryBuilder.build());
        items.forEach(System.out :: println);
    }

    // 聚合
    @org.junit.Test
    public void aggs()
    {
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        // 不查询任何结果, 添加过滤
        queryBuilder.withSourceFilter(new FetchSourceFilter(new String[]{}, null));
        // 添加聚合, 聚合的名称是brands, 按照brand字段聚合
        queryBuilder.addAggregation(AggregationBuilders.terms("brands").field("brand"));
        // 查询, 查询结果需要强转
        AggregatedPage<Item> aggs = (AggregatedPage<Item>)itemRepository.search(queryBuilder.build());
        // 根据聚合的名字获取聚合
        // 因为是利用String类型字段来进行的term聚合，所以结果要强转为StringTerm类型
        StringTerms brands = (StringTerms)aggs.getAggregation("brands");
        // 获取桶
        List<StringTerms.Bucket> buckets = brands.getBuckets();
        buckets.forEach(bucket -> {
            // key
            System.out.println(bucket.getKeyAsString());
            // count
            System.out.println(bucket.getDocCount());
        });
    }
    /*
    查询的json:
    GET /item/_search
    {
      "size": 0,
      "aggs": {
        "brands": {
          "terms": {
            "field": "brand"
          },
          "aggs": {
            "avg_price": {
              "avg": {
                "field": "price"
              }
            }
          }
        }
      }
    }

    返回的json:
      "aggregations": {
        "brands": {
          "doc_count_error_upper_bound": 0,
          "sum_other_doc_count": 0,
          "buckets": [
            {
              "key": "华为",
              "doc_count": 1,
              "avg_price": {
                "value": 4499
              }
            },
            {
              "key": "小米",
              "doc_count": 1,
              "avg_price": {
                "value": 3499
              }
            },
            {
              "key": "锤子",
              "doc_count": 1,
              "avg_price": {
                "value": 3699
              }
            }
          ]
        }
      }
     */

    // 聚合, 度量
    @org.junit.Test
    public void aggs2()
    {
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        // 不查询任何结果, 添加过滤
        queryBuilder.withSourceFilter(new FetchSourceFilter(new String[]{}, null));
        // 添加聚合, 聚合的名称是brands, 按照brand字段聚合, 并且求平均值
        queryBuilder.addAggregation(AggregationBuilders.terms("brands").field("brand")
                .subAggregation(AggregationBuilders.avg("avg_price").field("price")));
        // 查询, 查询结果需要强转
        AggregatedPage<Item> aggs = (AggregatedPage<Item>)itemRepository.search(queryBuilder.build());
        // 根据聚合的名字获取聚合
        // 因为是利用String类型字段来进行的term聚合，所以结果要强转为StringTerm类型
        StringTerms brands = (StringTerms)aggs.getAggregation("brands");
        // 获取桶
        List<StringTerms.Bucket> buckets = brands.getBuckets();
        buckets.forEach(bucket -> {
            // key
            System.out.println(bucket.getKeyAsString());
            // count
            System.out.println(bucket.getDocCount());
            // 获取子聚合结果
            Map<String, Aggregation> map = bucket.getAggregations().asMap();
            InternalAvg avg = (InternalAvg) map.get("avg_price");
            System.out.println(avg.getValue());
        });
    }
}
