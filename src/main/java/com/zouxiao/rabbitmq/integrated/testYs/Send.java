package com.zouxiao.rabbitmq.integrated.testYs;

import com.zouxiao.rabbitmq.integrated.entity.User;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * @Description:
 * @Auther: zouxiao
 * @Date: 2019/2/12 16:16
 */

@Component
public class Send {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendDelay(User user){
        CorrelationData correlationDataId = new CorrelationData(UUID.randomUUID().toString().replaceAll("-", ""));
        System.out.println("-----开始推送消息,一分钟之后获取消息（延时队列）-----   "+ user.toString());
        rabbitTemplate.convertAndSend("delay_exchange_t","key_zouxiao_delay",user,message -> {
            message.getMessageProperties().setExpiration(60*1000+""); //1分钟
            return message;
        },correlationDataId);
    }


    /*public static void main(String[] args) {
        for(int i=0;i<10;i++){
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            System.out.println(uuid);
        }
    }*/
}
