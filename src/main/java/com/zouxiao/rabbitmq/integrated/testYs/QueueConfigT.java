package com.zouxiao.rabbitmq.integrated.testYs;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description:
 * @Auther: zouxiao
 * @Date: 2019/2/12 15:43
 */

@Configuration
public class QueueConfigT {

    @Bean
    public Queue delayQueue(){
        Map<String,Object> param = new HashMap<>();
        param.put("x-dead-letter-exchange","user_exchange_t");
        param.put("x-dead-letter-routing-key","key_zouxiao");
        return new Queue("delay_queue_t",true,false,false,param);
    }

    @Bean
    public Queue userQueue(){
        return new Queue("user_queue_t",true);
    }
}
