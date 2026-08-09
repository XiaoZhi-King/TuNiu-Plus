package com.tuniu.common.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@TableName("orders")
public class Orders implements Serializable {

    @TableId(type = IdType.AUTO)
    private Integer id;

    @TableField("user_id")
    private Integer userId;
    private String time;
    private String status;
    @TableField("total_price")
    private Integer totalPrice;

    /** 关联的车票信息（非数据库字段） */
    @TableField(exist = false)
    private TrainTickets trainTickets;
    /** 关联的乘车人列表（非数据库字段） */
    @TableField(exist = false)
    private List<Passenger> passengers;
    /** 前端通过 is.passenger 访问的别名（非数据库字段） */
    @TableField(exist = false)
    private List<Passenger> passenger;
}
