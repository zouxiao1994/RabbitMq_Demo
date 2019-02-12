package com.zouxiao.rabbitmq.integrated;

import com.rabbitmq.client.Channel;
import com.zouxiao.rabbitmq.integrated.config.RabbitMqConfig;
import com.zouxiao.rabbitmq.integrated.entity.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;

/**
 * @Description:
 * @Auther: zouxiao
 * @Date: 2019/2/2 15:00
 */
@Component
@Slf4j
public class DelayReceiver {

    @RabbitListener(queues = {RabbitMqConfig.ORDER_QUEUE_NAME},containerFactory = "rabbitListenerContainerFactory")
    public void orderDelayQueue(Order order, Message message, Channel channel) throws IOException {
        log.info("###########################################");
        log.info("【orderDelayQueue 监听的消息】 - 【消费时间】 - [{}]- 【订单内容】 - [{}]",  new Date(), order.toString());
        if(order.getOrderStatus() == 0) {
            order.setOrderStatus(2);
            log.info("【该订单未支付，取消订单】" + order.toString());
        } else if(order.getOrderStatus() == 1) {
            log.info("【该订单已完成支付】");
        } else if(order.getOrderStatus() == 2) {
            log.info("【该订单已取消】");
        }
        channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
        log.info("###########################################");
    }
}

