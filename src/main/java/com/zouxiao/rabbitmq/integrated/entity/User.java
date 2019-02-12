package com.zouxiao.rabbitmq.integrated.entity;

import lombok.Data;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Description:
 * @Auther: zouxiao
 * @Date: 2019/2/1 14:09
 */
@Data
public class User implements Serializable {

    private static final long serialVersionUID = 3552965534589269727L;

    private String name;

    private String age;

    private Date bir;

    private Integer order;

    @Override
    public String toString() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return "User{" +
                "name='" + name + '\'' +
                ", age='" + age + '\'' +
                ", bir=" + format.format(bir) +
                ", order=" + order +
                '}';
    }
}
