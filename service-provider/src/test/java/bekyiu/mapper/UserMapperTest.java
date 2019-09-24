package bekyiu.mapper;

import bekyiu.AppProvider;
import bekyiu.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = AppProvider.class)
public class UserMapperTest
{

    @Autowired
    private UserMapper userMapper;
    @Test
    public void get()
    {
        User user = userMapper.get(1L);
        System.out.println("user = " + user);
    }
}