package com.zouxiao.rabbitmq.noIntegrated.txMq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.zouxiao.rabbitmq.noIntegrated.Util.ConnectionUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 *  订阅模式 Publish/Subscribe  事务机制
 *
 * @Description: 消息生产者 ---没有集成springboot
 * @Auther: zouxiao
 * @Date: 2019/1/30 14:12
 */
public class Send {

    private final static String EXCHANGE_NAME = "exchange_tx";

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        //获取连接
        Connection connection = ConnectionUtils.getConnection();

        //从连接中创建通道
        Channel channel = connection.createChannel();

        // 声明exchange 交换机 转发器
        channel.exchangeDeclare(EXCHANGE_NAME,"topic");

        //每个消费者发送确认信号之前，消息队列不发送下一个消息过来，一次只处理一个消息
        // 限制发给同一个消费者不得超过1条消息
        int prefetchCount = 1;
        channel.basicQos(prefetchCount);

        //消息
        String msg="hello exchange_topic";
        //这个时候消息是往交换机里发的，不再往队列里发了
        //String key = "error";
        String key = "news.info";

        try{
            channel.txSelect();
            channel.basicPublish(EXCHANGE_NAME,key,null,msg.getBytes());
            //int error = 1/0;   //用来模拟出现报错的场景
            channel.txCommit();
            System.out.println("Send ----- "+ msg);
        }catch (Exception e){
            e.printStackTrace();
            channel.txRollback();
            System.out.println("txRollback");
        }

        //关闭资源
        channel.close();
        connection.close();

    }
}
