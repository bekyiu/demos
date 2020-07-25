package bekyiu.mapper;

import bekyiu.domain.User;
import bekyiu.enums.Sex;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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
//        User u = User.builder()
//                .id(8)
//                .username("nanase77")
//                .build();
//        userMapper.updateByPrimaryKeySelective(u);
//        System.out.println(u);
    }
}