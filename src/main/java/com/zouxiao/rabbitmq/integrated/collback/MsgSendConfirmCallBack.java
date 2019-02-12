package com.zouxiao.rabbitmq.integrated.collback;

import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

/**
 * @Description:消息发送到交换机确认机制
 * @Auther: zouxiao
 * @Date: 2019/2/1 09:49
 */
public class MsgSendConfirmCallBack implements RabbitTemplate.ConfirmCallback {

    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        System.out.println("MsgSendConfirmCallBack  , 回调id:" + correlationData);
        if (ack) {
            System.out.println("消息投递成功");
        } else {
            System.out.println("消息投递失败:" + cause+"\n重新发送");
        }
    }
}
