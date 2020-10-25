package bekyiu.dao;

import bekyiu.domain.Comment;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author: wangyc
 * @Date: 2020/10/25 21:23
 *
 * 案例: https://www.jianshu.com/p/a576499769ae
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class CommentDaoTest
{
//    @Autowired
//    private CommentDao dao;

    @Autowired
    private MongoTemplate template;

    @Test
    public void save()
    {
        List<Comment> list = new ArrayList<>();
        for (int i = 0; i < 20; i++)
        {
            Comment c = new Comment();
            c.setContent("zzz" + i);
            c.setArticleId(i + "");
            c.setCreateDatetime(LocalDateTime.of(2020, 10, i + 1, 0, 0));
            c.setLikeNum(i * 5);
            c.setPublishTime(new Date(System.currentTimeMillis() - i * 10000000));
            c.setUserId("" + i * 3);
            list.add(c);
        }
        template.insertAll(list);
    }

    @Test
    public void findAll()
    {
//        template.findAll(Comment.class).forEach(System.out :: println);
        System.out.println(template.findById("5f9581438b01905b04974df3", Comment.class));
    }

    @Test
    public void find()
    {
        // core.Query
        Query query = new Query(Criteria
                .where("userId").is("3")
                .and("likeNum").gte(5));
        template.find(query, Comment.class).forEach(System.out :: println);
    }

    @Test
    public void page()
    {
        int currentPage = 2;
        int pageSize = 7;

        Query query = new Query();
        // 分页
        long totalCount = template.count(query, Comment.class);
        query = query.skip((currentPage - 1) * pageSize).limit(pageSize);

        List<Comment> comments = template.find(query, Comment.class);
        System.out.println("totalCount: " + totalCount);
        comments.forEach(System.out :: println);

    }
}
