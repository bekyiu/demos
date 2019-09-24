package bekyiu.controller;

import bekyiu.domain.User;
import bekyiu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;

@Controller
public class HelloController
{

    @Autowired
    private UserService userService;
    @RequestMapping("/users")
    @ResponseBody
    public List<User> list()
    {
        return userService.listAll();
    }

    @RequestMapping("/save")
    @ResponseBody
    public String save()
    {
        User u = new User();
        u.setName("sb");
        userService.save(u);
        return "";
    }

    @RequestMapping("/j")
    public String toJsp(Model model)
    {
        model.addAttribute("users", userService.listAll());
        User u = new User();
        u.setName("nihao");
        u.setHeadImg("/xx.png");
        u.setBornDate(new Date());
        u.setId(614L);
        model.addAttribute("single", u);
        return "test";
    }
}
