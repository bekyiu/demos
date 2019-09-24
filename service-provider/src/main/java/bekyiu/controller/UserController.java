package bekyiu.controller;

import bekyiu.domain.User;
import bekyiu.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController
{
    @Autowired
    private IUserService userService;

    @GetMapping("{id}")
    public User getUserById(@PathVariable("id") Long id)
    {
        return userService.getUserById(id);
    }
}
