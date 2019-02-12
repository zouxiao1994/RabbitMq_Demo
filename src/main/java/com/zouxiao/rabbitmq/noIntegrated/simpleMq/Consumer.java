package com.zouxiao.rabbitmq.noIntegrated.simpleMq;

import com.rabbitmq.client.*;
import com.zouxiao.rabbitmq.noIntegrated.Util.ConnectionUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 简单队列
 *
 * @Description: 消费者 ---没有集成springboot
 * @Auther: zouxiao
 * @Date: 2019/1/30 14:38
 */
public class Consumer {

    private static final String QUEUE_NAME="queue_simple_new";

    public static void main(String[] args) throws IOException, TimeoutException {
        //获取连接
        Connection connection = ConnectionUtils.getConnection();

        //从连接中创建通道
        Channel channel = connection.createChannel();

        // 声明队列，主要为了防止消息接收者先运行此程序，队列还不存在时创建队列。
        boolean durable=false;
        boolean exclusive=false;
        boolean autoDelete=false;
        channel.queueDeclare(QUEUE_NAME,durable,exclusive,autoDelete,null);

        DefaultConsumer defaultConsumer = new DefaultConsumer(channel){
            ////获取到达的消息
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String msg = new String(body,"utf-8");
                System.out.println("Recv ----- " + msg);
            }
        };

        //监听队列
        boolean autoAck = true; //消息的确认模式自动应答
        channel.basicConsume(QUEUE_NAME,autoAck,defaultConsumer);

    }

}
