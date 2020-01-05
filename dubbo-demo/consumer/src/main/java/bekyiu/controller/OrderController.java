package bekyiu.controller;

import bekyiu.domain.UserAddress;
import bekyiu.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class OrderController
{
    @Autowired
    private IOrderService orderService;

    @ResponseBody
    @GetMapping("/list")
    public List<UserAddress> list(Long uid)
    {
        return orderService.initOrder(uid);
    }
}
