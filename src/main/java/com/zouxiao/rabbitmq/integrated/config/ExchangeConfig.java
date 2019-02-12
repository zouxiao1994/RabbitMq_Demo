package com.zouxiao.rabbitmq.integrated.config;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description: 消息交换机配置  可以配置多个
 * @Auther: zouxiao
 * @Date: 2019/2/1 09:42
 */
@Configuration
public class ExchangeConfig {

    /**
     * 1.定义direct exchange，绑定queueTest
     * 2.durable="true" rabbitmq重启的时候不需要创建新的交换机
     * 3.direct交换器相对来说比较简单，匹配规则为：如果路由键匹配，消息就被投送到相关的队列
     * fanout交换器中没有路由键的概念，他会把消息发送到所有绑定在此交换器上面的队列中。
     * topic交换器你采用模糊匹配路由键的原则进行转发消息到队列中
     * key: queue在该direct-exchange中的key值，当消息发送给direct-exchange中指定key为设置值时，
     * 消息将会转发给queue参数指定的消息队列
     */
    @Bean
    public DirectExchange directExchange() {
        DirectExchange directExchange = new DirectExchange(RabbitMqConfig.EXCHANGE, true, false);
        return directExchange;
    }

    //topic
    @Bean
    public TopicExchange topicExchange() {
        TopicExchange topicExchange = new TopicExchange(RabbitMqConfig.EXCHANGE2, true, false);
        return topicExchange;
    }


    @Bean
    public TopicExchange topicExchange3() {
        TopicExchange topicExchange = new TopicExchange(RabbitMqConfig.EXCHANGE3, true, false);
        return topicExchange;
    }

    /**
     * 需要将一个队列绑定到交换机上，要求该消息与一个特定的路由键完全匹配。
     * 这是一个完整的匹配。如果一个队列绑定到该交换机上要求路由键 “dog”，则只有被标记为“dog”的消息才被转发，
     * 不会转发dog.puppy，也不会转发dog.guard，只会转发dog。
     * @return DirectExchange
     */
    @Bean
    public DirectExchange orderDelayExchange() {
        return new DirectExchange(RabbitMqConfig.ORDER_DELAY_EXCHANGE);
    }


    /**
     * 将路由键和某模式进行匹配。此时队列需要绑定要一个模式上。
     * 符号“#”匹配一个或多个词，符号“*”匹配不多不少一个词。因此“audit.#”能够匹配到“audit.irs.corporate”，但是“audit.*” 只会匹配到“audit.irs”。
     **/
    @Bean
    public TopicExchange orderTopicExchange() {
        return new TopicExchange(RabbitMqConfig.ORDER_EXCHANGE_NAME);
    }


}
