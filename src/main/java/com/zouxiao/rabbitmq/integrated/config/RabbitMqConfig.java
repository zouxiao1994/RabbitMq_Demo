package com.zouxiao.rabbitmq.integrated.config;


import com.zouxiao.rabbitmq.integrated.collback.MsgSendConfirmCallBack;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * RabbitMq配置
 * @author zouxiao
 * @date 2019/2/1 09:48
 */
@Configuration
public class RabbitMqConfig {

    /** 消息交换机的名字*/
    public static final String EXCHANGE = "exchangeTest";

    /** 消息交换机的名字*/
    public static final String EXCHANGE2 = "exchangeTest2";

    /** 消息交换机的名字*/
    public static final String EXCHANGE3 = "exchangeTest3";

    /** 队列key1*/
    public static final String ROUTINGKEY1 = "queue_one_key1";
    /** 队列key2*/
    public static final String ROUTINGKEY2 = "queue_one_key2";

    public static final String ROUTINGKEY3 = "new.#";

    /**
     * 延迟队列 TTL 名称
     */
    public static final String ORDER_DELAY_QUEUE = "user.order.delay.queue";
    /**
     * DLX，dead letter发送到的 exchange
     * 延时消息就是发送到该交换机的
     */
    public static final String ORDER_DELAY_EXCHANGE = "user.order.delay.exchange";
    /**
     * routing key 名称
     * 具体消息发送在该 routingKey 的
     */
    public static final String ORDER_DELAY_ROUTING_KEY = "order_delay";

    public static final String ORDER_QUEUE_NAME = "user.order.queue";
    public static final String ORDER_EXCHANGE_NAME = "user.order.exchange";
    public static final String ORDER_ROUTING_KEY = "order";




    @Autowired
    private QueueConfig queueConfig;
    @Autowired
    private ExchangeConfig exchangeConfig;

    /**
     * 连接工厂
     */
    @Autowired
    private ConnectionFactory connectionFactory;

    /**
     将消息队列1和交换机进行绑定
     */
    @Bean
    public Binding binding_one() {
        return BindingBuilder.bind(queueConfig.firstQueue()).to(exchangeConfig.directExchange()).with(RabbitMqConfig.ROUTINGKEY1);
    }

    /**
     * 将消息队列2和交换机进行绑定
     */
 /*   @Bean
    public Binding binding_two() {
        return BindingBuilder.bind(queueConfig.secondQueue()).to(exchangeConfig.directExchange()).with(RabbitMqConfig.ROUTINGKEY2);
    }*/

    @Bean
    public Binding binding_1() {
        return BindingBuilder.bind(queueConfig.firstQueue()).to(exchangeConfig.topicExchange()).with(RabbitMqConfig.ROUTINGKEY3);
    }

    @Bean
    public Binding binding_2() {
        return BindingBuilder.bind(queueConfig.secondQueue()).to(exchangeConfig.topicExchange()).with(RabbitMqConfig.ROUTINGKEY3);
    }

    @Bean
    public Binding binding_3() {
        return BindingBuilder.bind(queueConfig.thridQueue()).to(exchangeConfig.topicExchange3()).with(RabbitMqConfig.ROUTINGKEY3);
    }

    @Bean
    public Binding dlxBinding() {
        return BindingBuilder.bind(queueConfig.delayOrderQueue()).to(exchangeConfig.orderDelayExchange()).with(ORDER_DELAY_ROUTING_KEY);
    }

    @Bean
    public Binding orderBinding() {
        //  如果要让延迟队列之间有关联,这里的 routingKey 和 绑定的交换机很关键
        return BindingBuilder.bind(queueConfig.orderQueue()).to(exchangeConfig.orderTopicExchange()).with(ORDER_ROUTING_KEY);
    }



    /**
     * queue listener  观察 监听模式
     * 当有消息到达时会通知监听在对应的队列上的监听对象
     * @return
     */
   /*@Bean
    public SimpleMessageListenerContainer simpleMessageListenerContainer_one(){
        SimpleMessageListenerContainer simpleMessageListenerContainer = new SimpleMessageListenerContainer(connectionFactory);
        simpleMessageListenerContainer.setQueues(queueConfig.firstQueue(),queueConfig.secondQueue());
        simpleMessageListenerContainer.setExposeListenerChannel(true);
        simpleMessageListenerContainer.setMaxConcurrentConsumers(5);
        simpleMessageListenerContainer.setConcurrentConsumers(1);
        simpleMessageListenerContainer.setAcknowledgeMode(AcknowledgeMode.MANUAL); //设置确认模式手工确认
       //确认消息（全局处理消息）
        simpleMessageListenerContainer.setMessageListener((ChannelAwareMessageListener) (message, channel) -> {      //消息处理
           System.out.println("====接收到消息=====");
          // System.out.println(new String(message.getBody()));
           if(message.getMessageProperties().getHeaders().get("error") == null){
               channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
               System.out.println("消息已经确认");
           }else {
               //channel.basicNack(message.getMessageProperties().getDeliveryTag(),false,false);
               channel.basicReject(message.getMessageProperties().getDeliveryTag(),false);
               System.out.println("消息拒绝");
           }

        });
        return simpleMessageListenerContainer;
    }*/

    /**
     * 定义rabbit template用于数据的接收和发送
     * @return
     */
    @Bean
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        /**若使用confirm-callback或return-callback，
         * 必须要配置publisherConfirms或publisherReturns为true
         * 每个rabbitTemplate只能有一个confirm-callback和return-callback
         */
        template.setConfirmCallback(msgSendConfirmCallBack());
        //template.setReturnCallback(msgSendReturnCallback());
        /**
         * 使用return-callback时必须设置mandatory为true，或者在配置中设置mandatory-expression的值为true，
         * 可针对每次请求的消息去确定’mandatory’的boolean值，
         * 只能在提供’return -callback’时使用，与mandatory互斥
         */
        //  template.setMandatory(true);
        return template;
    }

    /**
     * 消息确认机制
     * Confirms给客户端一种轻量级的方式，能够跟踪哪些消息被broker处理，
     * 哪些可能因为broker宕掉或者网络失败的情况而重新发布。
     * 确认并且保证消息被送达，提供了两种方式：发布确认和事务。(两者不可同时使用)
     * 在channel为事务时，不可引入确认模式；同样channel为确认模式下，不可使用事务。
     * @return
     */
    @Bean
    public MsgSendConfirmCallBack msgSendConfirmCallBack(){
        return new MsgSendConfirmCallBack();
    }

}
