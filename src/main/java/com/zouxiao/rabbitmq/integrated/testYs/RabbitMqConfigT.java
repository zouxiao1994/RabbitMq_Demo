package com.zouxiao.rabbitmq.integrated.testYs;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description:
 * @Auther: zouxiao
 * @Date: 2019/2/12 15:51
 */
@Configuration
public class RabbitMqConfigT {

    @Autowired
    private QueueConfigT queueConfigT;

    @Autowired
    private ExchangeConfigT exchangeConfigT;

    /**
     * 连接工厂
     */
    @Autowired
    private ConnectionFactory connectionFactory;


    //死信队列绑定
    @Bean
    public Binding delayBing(){
        return BindingBuilder.bind(queueConfigT.delayQueue()).to(exchangeConfigT.delayExchange()).with("key_zouxiao_delay");
    }


    //正常队列绑定
    @Bean
    public Binding userBing(){
        return BindingBuilder.bind(queueConfigT.userQueue()).to(exchangeConfigT.userExchange()).with("key_zouxiao");
    }



}
