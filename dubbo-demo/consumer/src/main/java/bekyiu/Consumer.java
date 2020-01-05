package bekyiu;

import bekyiu.service.IOrderService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Consumer
{
    public static void main(String[] args) throws Exception
    {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("consumer.xml");
        context.start();
        IOrderService orderService = (IOrderService) context.getBean("orderServiceImpl");
        orderService.initOrder(1L);
        System.in.read();
    }
}
