package bekyiu.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: wangyc
 * @Date: 2020/7/15 16:25
 */
@Data
@AllArgsConstructor
public class Order implements Serializable
{
    private int id;
    private String type;

    public static List<Order> list()
    {
        List<Order> list = new ArrayList<>();
        list.add(new Order(111, "创建订单"));
        list.add(new Order(222, "创建订单"));
        list.add(new Order(333, "创建订单"));
        list.add(new Order(111, "支付订单"));
        list.add(new Order(111, "结束订单"));
        list.add(new Order(333, "支付订单"));
        list.add(new Order(222, "支付订单"));
        list.add(new Order(222, "结束订单"));
        list.add(new Order(333, "结束订单"));
        return list;
    }
}
