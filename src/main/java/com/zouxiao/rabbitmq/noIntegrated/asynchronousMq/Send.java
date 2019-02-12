package com.zouxiao.rabbitmq.noIntegrated.asynchronousMq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmListener;
import com.rabbitmq.client.Connection;
import com.zouxiao.rabbitmq.noIntegrated.Util.ConnectionUtils;

import java.io.IOException;
import java.util.Collections;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.TimeoutException;

/**
 *  普通 confirm 模式
 *
 * @Description: 消息生产者 ---没有集成springboot
 * @Auther: zouxiao
 * @Date: 2019/1/30 14:12
 */
public class Send {

    private final static String EXCHANGE_NAME = "exchange_confirm";

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

        //生产者通过调用channel的confirmSelect方法将channel设置为confirm模式
        channel.confirmSelect();
        final SortedSet<Long> confirmSet = Collections.synchronizedSortedSet(new TreeSet<Long>());
       channel.addConfirmListener(new ConfirmListener() {
           //每回调一次handleAck方法，unconfirm集合删掉相应的一条（multiple=false） 或多条（multiple=true）记录。
           @Override
           public void handleAck(long deliveryTag, boolean multiple) throws IOException {
                if(multiple){
                    System.out.println("--multiple-- " + multiple);
                    confirmSet.headSet(deliveryTag+1).clear();
                }else {
                    System.out.println("--multiple-- " + multiple);
                    confirmSet.remove(deliveryTag);
                }
           }

           @Override
           public void handleNack(long deliveryTag, boolean multiple) throws IOException {
               System.out.println("Nack, SeqNo: " + deliveryTag + ", multiple: " + multiple);

               if(multiple){
                   System.out.println("--multiple-- " + multiple);
                   confirmSet.headSet(deliveryTag+1).clear();
               }else {
                   System.out.println("--multiple-- " + multiple);
                   confirmSet.remove(deliveryTag);
               }
           }
       });


        for (int i = 0; i < 100; i++) {
           //消息
           String msg="hello exchange_topic";
           String key = "news.info";
           long nextSeqNo = channel.getNextPublishSeqNo();
           channel.basicPublish(EXCHANGE_NAME,key,null,msg.getBytes());
            confirmSet.add(nextSeqNo);
       }



    }
}
