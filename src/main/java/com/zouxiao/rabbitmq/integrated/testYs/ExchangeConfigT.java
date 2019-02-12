package com.zouxiao.rabbitmq.integrated.testYs;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description:
 * @Auther: zouxiao
 * @Date: 2019/2/12 15:49
 */
@Configuration
public class ExchangeConfigT {

    @Bean
    public DirectExchange delayExchange(){
        return new DirectExchange("delay_exchange_t");
    }

    @Bean
    public TopicExchange userExchange(){
        return new TopicExchange("user_exchange_t");
    }
}
