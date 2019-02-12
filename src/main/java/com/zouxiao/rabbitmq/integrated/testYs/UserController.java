package com.zouxiao.rabbitmq.integrated.testYs;

import com.zouxiao.rabbitmq.integrated.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * @Description:
 * @Auther: zouxiao
 * @Date: 2019/2/12 16:33
 */
@RestController
public class UserController {

    @Autowired
    private Send send;

    @GetMapping("/testUser")
    public String testUser() throws ParseException {
        User user = new User();
        user.setName("老李");
        user.setAge("35");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        user.setBir(format.parse("1980-02-22"));
        user.setOrder(1);
        send.sendDelay(user);
        return "success";
    }

}
