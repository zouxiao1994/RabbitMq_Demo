package com.zouxiao.rabbitmq.integrated.testYs;

import com.rabbitmq.client.Channel;
import com.zouxiao.rabbitmq.integrated.entity.User;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @Description:
 * @Auther: zouxiao
 * @Date: 2019/2/12 16:28
 */
@Component
public class Receiver {

    @RabbitListener(queues = {"user_queue_t"},containerFactory = "rabbitListenerContainerFactory")
    public void userReceiver(User user , Channel channel , Message message) throws IOException {
        System.out.println("------消费者获取到的内容："+ user.toString());
        //手动回应
        channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
    }
}
