package com.zouxiao.rabbitmq.integrated.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description:
 * @Auther: zouxiao
 * @Date: 2019/2/2 15:00
 */

@Data
public class Order implements Serializable {

    private static final long serialVersionUID = -308128062571446874L;

    private String orderId; // 订单id

    private Integer orderStatus; // 订单状态 0：未支付，1：已支付，2：订单已取消

    private String orderName; // 订单名字

}
