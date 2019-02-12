package com.zouxiao.rabbitmq.noIntegrated.roundrobinMq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.zouxiao.rabbitmq.noIntegrated.Util.ConnectionUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Round-robin（轮询分发）
 *
 * @Description: 消息生产者 ---没有集成springboot
 * @Auther: zouxiao
 * @Date: 2019/1/30 14:12
 */
public class Send {

    private static final String QUEUE_NAME="queue_work";

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        //获取连接
        Connection connection = ConnectionUtils.getConnection();

        //从连接中创建通道
        Channel channel = connection.createChannel();

        //创建队列 (声明)  因为我们要往队列里面发送消息,这是后就得知道往哪个队列中发送,就好比在哪个管子里面放 水
        boolean durable=false;
        boolean exclusive=false;
        boolean autoDelete=false;
        channel.queueDeclare(QUEUE_NAME,durable,exclusive,autoDelete,null);



        //第一个参数是exchangeName(默认情况下代理服务器端是存在一个""名字的exchange的,
        // 因此如果不创建exchange的话我们可以直接将该参数设置成"",如果创建了exchange的话
        // 我们需要将该参数设置成创建的exchange的名字)
        for (int i = 0; i < 20; i++) {
            //消息
            String msg="work queue ---- " +i;
            channel.basicPublish("",QUEUE_NAME,null,msg.getBytes());
            System.out.println("Send ----- "+ msg);

            Thread.sleep(10*i);
        }


        //关闭资源
        channel.close();
        connection.close();

    }
}
