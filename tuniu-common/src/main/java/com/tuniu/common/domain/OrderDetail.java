package com.tuniu.common.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("order_detail")
public class OrderDetail implements Serializable {

    @TableId(type = IdType.AUTO)
    private Integer id;

    @TableField("order_id")
    private Integer orderId;
    @TableField("ticket_type")
    private String ticketType;
    @TableField("ticket_id")
    private Integer ticketId;
    @TableField("passenger_id")
    private Integer passengerId;
    @TableField("buy_num")
    private Integer buyNum;
}
