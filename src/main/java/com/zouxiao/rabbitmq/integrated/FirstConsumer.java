package com.zouxiao.rabbitmq.integrated;

import com.rabbitmq.client.Channel;
import com.zouxiao.rabbitmq.integrated.entity.User;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @Description: 消息消费者1
 * @Auther: zouxiao
 * @Date: 2019/2/1 09:57
 */
@Component
public class FirstConsumer {

   /* @RabbitListener(queues = {"first-queue","second-queue"}, containerFactory = "rabbitListenerContainerFactory")
    public void handleMessage(String message1, Channel channel, Message message) throws Exception {
        //手动回应
        //告诉服务器收到这条消息 已经被我消费了 可以在队列删掉 这样以后就不会再发了 否则消息服务器以为这条消息没处理掉 后续还会在发
        channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
        // 处理消息
        System.out.println("FirstConsumer {} handleMessage :"+message1);
    }*/


    //消费者1
    @RabbitListener(queues = {"first-queue1"}, containerFactory = "rabbitListenerContainerFactory")
    public void handleMessage1(User user, Channel channel, Message message) throws Exception {
        //System.out.println("=====handleMessage2======");
        //手动回应
        //告诉服务器收到这条消息 已经被我消费了 可以在队列删掉 这样以后就不会再发了 否则消息服务器以为这条消息没处理掉 后续还会在发
        channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
        // 处理消息
        System.out.println("handleMessage1 :"+user.toString());
    }


   //消费者2
    @RabbitListener(queues = {"second-queue2"}, containerFactory = "rabbitListenerContainerFactory")
    public void handleMessage2(User user, Channel channel, Message message) throws Exception {
       // System.out.println("=====handleMessage3======");
        //手动回应
        //告诉服务器收到这条消息 已经被我消费了 可以在队列删掉 这样以后就不会再发了 否则消息服务器以为这条消息没处理掉 后续还会在发
        //延迟
        //Thread.sleep(1500);
        channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
        // 处理消息
        System.out.println("handleMessage2:"+user.toString());
    }


    //消费者3
    @RabbitListener(queues = {"thrid-queue3"}, containerFactory = "rabbitListenerContainerFactory")
    public void handleMessage3(User user, Channel channel, Message message) throws Exception {
        // System.out.println("=====handleMessage3======");
        //手动回应
        //告诉服务器收到这条消息 已经被我消费了 可以在队列删掉 这样以后就不会再发了 否则消息服务器以为这条消息没处理掉 后续还会在发
        //延迟
        //Thread.sleep(1500);
        channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
        // 处理消息
        System.out.println("handleMessage3:"+user.toString());
    }
}
