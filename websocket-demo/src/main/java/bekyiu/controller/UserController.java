package bekyiu.controller;

import bekyiu.ws.ChatEndpoint;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

/**
 * @Author: wangyc
 * @Date: 2020/10/13 16:03
 */
@RestController
public class UserController
{

    // 返回在线用户列表
    @GetMapping("/onlineUsers")
    public Set<String> onlineUsers()
    {
        return ChatEndpoint.getOnlineUsers();
    }
}
