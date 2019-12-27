package bekyiu.spring;

import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

// 消费者
// 注入Spring容器后再加上@RabbitListener注解即可自动监听
@Component
public class Listener {

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "spring.test.queue", durable = "true"), // 从哪个队列获取消息
            exchange = @Exchange( // 这个队列绑定了哪个交换机
                    value = "spring.test.exchange",
                    ignoreDeclarationExceptions = "true",
                    type = ExchangeTypes.TOPIC
            ),
            key = {"#.#"})) // 交换机的路由规则
    public void listen(String msg) // 什么类型的消息参数就写什么类型
    {
        // 如何处理消息
        System.out.println("接收到消息：" + msg);
    }
}
