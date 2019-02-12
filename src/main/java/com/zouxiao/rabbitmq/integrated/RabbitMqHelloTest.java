package com.zouxiao.rabbitmq.integrated;

import com.zouxiao.rabbitmq.integrated.entity.Order;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Description: rabbit集成springboot 单元测试类
 * @Auther: zouxiao
 * @Date: 2019/2/1 09:11
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RabbitMqHelloTest {

    @Autowired
    private FirstSender firstSender;

    @Autowired
    private DelaySender delaySender;

    @Test
    public void hello() throws Exception {
        for (int i = 0; i < 20 ; i++) {
            firstSender.send("123456zx","msg----ello",i+1);
        }
    }





}
