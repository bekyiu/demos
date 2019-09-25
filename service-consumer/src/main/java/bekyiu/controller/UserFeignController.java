package bekyiu.controller;

import bekyiu.client.UserClient;
import bekyiu.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/consumer/feign/users")
public class UserFeignController
{
    @Autowired
    private UserClient userClient;

    @GetMapping("{id}")
    public User getUserById(@PathVariable("id") Long id)
    {
        if(id == 1)
        {
            throw new RuntimeException();
        }
        return userClient.getUserById(id);
    }
}
