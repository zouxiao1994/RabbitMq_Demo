package com.zouxiao.rabbitmq.noIntegrated.txMq;

import com.rabbitmq.client.*;
import com.zouxiao.rabbitmq.noIntegrated.Util.ConnectionUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 *  订阅模式 Publish/Subscribe     事务机制
 *
 * @Description: 消费者 ---没有集成springboot
 * @Auther: zouxiao
 * @Date: 2019/1/30 14:38
 */
public class Consumer2 {

    //队列名称  -- 多个消费者的队列不能一样
    private static final String QUEUE_NAME="queue_exchange_tx2";

    //交换机名称
    private final static String EXCHANGE_NAME = "exchange_tx";

    public static void main(String[] args) throws IOException, TimeoutException {
        //获取连接
        Connection connection = ConnectionUtils.getConnection();

        //从连接中创建通道
        Channel channel = connection.createChannel();

        // 声明队列，主要为了防止消息接收者先运行此程序，队列还不存在时创建队列。
        boolean durable=true;  //消息持久化
        boolean exclusive=false;
        boolean autoDelete=false;
        channel.queueDeclare(QUEUE_NAME,durable,exclusive,autoDelete,null);

        // 绑定队列到交换机 --这里只绑定了error的
        String key1 = "news.#";
        channel.queueBind(QUEUE_NAME,EXCHANGE_NAME,key1);

        int prefetchCount = 1;
        channel.basicQos(prefetchCount);

        DefaultConsumer defaultConsumer = new DefaultConsumer(channel){
            ////获取到达的消息
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String msg = new String(body,"utf-8");
                System.out.println("Consumer2  ----- " + msg);
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    //手动确认
                    channel.basicAck(envelope.getDeliveryTag(),false);
                }
            }
        };

        //监听队列
        boolean autoAck = false; //改为手动应答
        channel.basicConsume(QUEUE_NAME,autoAck,defaultConsumer);

    }

}
