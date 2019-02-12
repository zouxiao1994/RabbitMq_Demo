package com.zouxiao.rabbitmq.integrated;


import com.zouxiao.rabbitmq.integrated.config.RabbitMqConfig;
import com.zouxiao.rabbitmq.integrated.entity.User;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @Description: 消息发送  生产者1
 * @Auther: zouxiao
 * @Date: 2019/2/1 09:54
 */
@Component
public class FirstSender {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 发送消息
     * @param uuid
     * @param message  消息
     */
    public void send(String uuid,Object message,Integer index) {
        CorrelationData correlationId = new CorrelationData(uuid);
        //传User对象，springboot以及完美的支持对象的发送和接收，不需要格外的配置。
        User user = new User();
        user.setName("zouxiao");
        user.setAge("20");
        user.setBir(new Date());
        user.setOrder(index);
        rabbitTemplate.convertAndSend(RabbitMqConfig.EXCHANGE, RabbitMqConfig.ROUTINGKEY1,
                user, correlationId);
    }

}

