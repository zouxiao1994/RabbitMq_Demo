package com.zouxiao.rabbitmq.noIntegrated.Util;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Description:  获取MQ 连接 --没有集成springboot
 * @Auther: zouxiao
 * @Date: 2019/1/30 11:34
 */
public class ConnectionUtils {

    public static Connection getConnection() throws IOException, TimeoutException {
        //定义链接工厂
        ConnectionFactory connectionFactory = new ConnectionFactory();

        //设置服务地址
        connectionFactory.setHost("127.0.0.1");
        //端口
        connectionFactory.setPort(5672);
        ///设置账号信息，用户名、密码、vhost
        connectionFactory.setVirtualHost("/vhost_zx");
        connectionFactory.setUsername("zx");
        connectionFactory.setPassword("123456");

        //获取链接
        Connection connection = connectionFactory.newConnection();
        return  connection;
    }
}
