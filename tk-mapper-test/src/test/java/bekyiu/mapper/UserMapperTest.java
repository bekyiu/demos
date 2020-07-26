package bekyiu.mapper;

import bekyiu.domain.User;
import bekyiu.enums.Sex;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @Author: wangyc
 * @Date: 2020/7/25 13:34
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class UserMapperTest
{
    @Autowired
    private UserMapper userMapper;

    @Before
    public void before()
    {
        System.out.println("==========================================");
    }

    @After
    public void after()
    {
        System.out.println("==========================================");
    }

    @Test
    public void t()
    {
        userMapper.selectAll().forEach(System.out :: println);
    }

    @Test
    public void t1()
    {
        System.out.println(userMapper.selectByPrimaryKey(1));
    }

    @Test
    public void t2()
    {
        User u = new User();
        u.setUsername("bekyiu");
        u.setPassword("614");
        u.setSex(Sex.MAN);
        userMapper.insert(u);
        System.out.println(u);
    }

    @Test
    public void t3()
    {
        // 获取第一页内容，每页5条，只对紧跟着的一次查询生效
        PageHelper.startPage(1, 5);
        userMapper.selectAll().forEach(System.out :: println);
        System.out.println("****************");
        userMapper.selectAll().forEach(System.out :: println);
    }

    @Test
    public void t4()
    {
        PageHelper.startPage(1, 5);
        List<User> list = userMapper.selectAll();
        PageInfo<User> pageInfo = new PageInfo<>(list);

    }


    @Test
    public void t5()
    {
        User u = new User();
        u.setSex(Sex.MAN);
        u.setPassword("09614");
        u.setUsername("Louise");
        userMapper.xmlInsert(u);
        userMapper.xmlList().forEach(System.out :: println);
    }
}