package com.zouxiao.rabbitmq.integrated;

import com.zouxiao.rabbitmq.integrated.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description:
 * @Auther: zouxiao
 * @Date: 2019/2/2 15:14
 */

@RestController
public class TestController {

    @Autowired
    private DelaySender delaySender;

    @Autowired
    private FirstSender firstSender;

    @GetMapping("/sendDelay")
    public Object sendDelay() {
        Order order1 = new Order();
        order1.setOrderStatus(0);
        order1.setOrderId("123456");
        order1.setOrderName("小米6");

        Order order2 = new Order();
        order2.setOrderStatus(1);
        order2.setOrderId("456789");
        order2.setOrderName("小米8");

        delaySender.sendDelay(order1);
        delaySender.sendDelay(order2);
        return "ok";
    }



    @GetMapping("/send1")
    public Object send1() {
        for (int i = 0; i < 10 ; i++) {
            firstSender.send("123456zx","msg----ello",i+1);
        }
        return "ok";
    }
}

