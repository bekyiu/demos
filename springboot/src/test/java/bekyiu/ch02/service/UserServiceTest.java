package bekyiu.ch02.service;

import bekyiu.ch02.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest
{

    @Autowired
    private UserService service;

    @Test
    public void save()
    {
        service.save(new User());
    }

    @Test
    public void listAll()
    {
        service.listAll().forEach(System.out :: println);
    }
}