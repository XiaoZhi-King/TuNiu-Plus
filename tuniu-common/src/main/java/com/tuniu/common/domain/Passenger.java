package com.tuniu.common.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("passenger")
public class Passenger implements Serializable {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private String name;
    private String type;
    @TableField("id_card")
    private String idCard;
    private String phone;
    @TableField("user_id")
    private Integer userId;
    @TableField("card_type")
    private String cardType;
    private String country;
    private String gender;
    private String often;
}
