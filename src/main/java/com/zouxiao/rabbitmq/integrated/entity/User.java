package com.zouxiao.rabbitmq.integrated.entity;

import lombok.Data;

import java.io.Serializable;
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
}
