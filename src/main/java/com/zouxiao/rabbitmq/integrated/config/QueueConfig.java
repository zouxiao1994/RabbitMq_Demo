package com.zouxiao.rabbitmq.integrated.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

import static com.zouxiao.rabbitmq.integrated.config.RabbitMqConfig.ORDER_EXCHANGE_NAME;
import static com.zouxiao.rabbitmq.integrated.config.RabbitMqConfig.ORDER_ROUTING_KEY;


/**
 * @Description: 队列配置  可以配置多个队列
 * @Auther: zouxiao
 * @Date: 2019/1/31 16:46
 */
@Configuration
public class QueueConfig {

    @Bean
    public Queue firstQueue() {
        /**
         durable="true" 持久化 rabbitmq重启的时候不需要创建新的队列
         auto-delete 表示消息队列没有在使用时将被自动删除 默认是false
         exclusive  表示该消息队列是否只在当前connection生效,默认是false
         */
        return new Queue("first-queue1",true,false,false);
    }

    @Bean
    public Queue secondQueue() {
        return new Queue("second-queue2",true,false,false);
    }

    @Bean
    public Queue thridQueue() {
        return new Queue("thrid-queue3",true,false,false);
    }

    @Bean
    public Queue delayOrderQueue() {
        Map<String, Object> params = new HashMap<>();
        // x-dead-letter-exchange 声明了队列里的死信转发到的DLX名称，
        params.put("x-dead-letter-exchange", ORDER_EXCHANGE_NAME);
        // x-dead-letter-routing-key 声明了这些死信在转发时携带的 routing-key 名称。
        params.put("x-dead-letter-routing-key", ORDER_ROUTING_KEY);
        return new Queue(RabbitMqConfig.ORDER_DELAY_QUEUE, true, false, false, params);
    }

    @Bean
    public Queue orderQueue() {
        return new Queue(RabbitMqConfig.ORDER_QUEUE_NAME, true);
    }


}
