package bekyiu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class HelloController
{
    @RequestMapping(value = "/hello")
    public String hello(HttpServletRequest req, HttpServletResponse resp)
    {
        return "你好";
    }

    @RequestMapping("/jsonp")
    public String jsonp(String callback)
    {
        String json = "{\"name\":\"Louise\", \"age\":16}";
        return callback + "(" + json + ")";
    }
}
