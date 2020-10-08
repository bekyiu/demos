package bekyiu.controller;

import bekyiu.domain.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * @Author: wangyc
 * @Date: 2020/10/8 15:33
 */
@RestController
public class DataController
{
    @GetMapping("/users")
    public ResponseEntity<Map<String, Object>> data()
    {
        List<User> list = new ArrayList<>();
        for (int i = 0; i < 20; i++)
        {
            User u = User.builder()
                    .id((long) i)
                    .phone("135" + i)
                    .username("username" + i)
                    .realName("realName" + i)
                    .hireDate(new Date(System.currentTimeMillis() - i * 100000000))
                    .status(i % 2 == 0)
                    .build();
            list.add(u);
        }
        Map<String, Object> res = new HashMap<>();
        res.put("code", "2000");
        res.put("data", list);
        return ResponseEntity.ok(res);
    }

    @PostMapping("/save")
    public ResponseEntity<Map<String, Object>> save(@RequestBody User u)
    {
        System.out.println(u);
        Map<String, Object> res = new HashMap<>();
        res.put("code", "2000");
        res.put("data", "保存成功!");
        return ResponseEntity.ok(res);
    }
}
