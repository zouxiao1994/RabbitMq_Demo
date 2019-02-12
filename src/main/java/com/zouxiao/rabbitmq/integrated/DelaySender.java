package com.zouxiao.rabbitmq.integrated;

import com.zouxiao.rabbitmq.integrated.config.RabbitMqConfig;
import com.zouxiao.rabbitmq.integrated.entity.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @Description:
 * @Auther: zouxiao
 * @Date: 2019/2/2 15:02
 */
@Component
@Slf4j
public class DelaySender {

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendDelay(Order order) {
       CorrelationData correlationId = new CorrelationData(Double.toString(Math.random()));
        log.info("【订单生成时间】" + new Date().toString() +"【1分钟后检查订单是否已经支付】" + order.toString() );
        rabbitTemplate.convertAndSend(RabbitMqConfig.ORDER_DELAY_EXCHANGE, RabbitMqConfig.ORDER_DELAY_ROUTING_KEY, order, message -> {
            // 如果配置了 params.put("x-message-ttl", 5 * 1000); 那么这一句也可以省略,具体根据业务需要是声明 Queue 的时候就指定好延迟时间还是在发送自己控制时间
            message.getMessageProperties().setExpiration(1 * 1000 * 60 + "");
            //message.getMessageProperties().setCorrelationId(Double.toString(Math.random()));
            return message;
        },correlationId);
    }
}

