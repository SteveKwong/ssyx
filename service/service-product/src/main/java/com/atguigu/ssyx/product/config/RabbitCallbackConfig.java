package com.atguigu.ssyx.product.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * @author kuanggong
 * @date 2023/7/18 15:34
 * @project_name atguigu-ssyx-parent
 */
@Configuration
@Slf4j
public class RabbitCallbackConfig {
    @Autowired
    private RabbitTemplate rabbitTemplate;


    @PostConstruct
    public void initRabbitTemplate() {
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            System.out.println("\n确认消息送到交换机(Exchange)结果：");
            System.out.println("相关数据：" + correlationData);
            System.out.println("是否成功：" + ack);
            System.out.println("错误原因：" + cause);
        });
        rabbitTemplate.setReturnCallback((message, replyCode, replyText, exchange, routingKey) -> {
            System.out.println("\n确认消息送到队列(Queue)结果：");
            System.out.println("发生消息：" + message);
            System.out.println("回应码：" + replyCode);
            System.out.println("回应信息：" + replyText);
            System.out.println("交换机：" + exchange);
            System.out.println("路由键：" + routingKey);
        });
    }


}
