package com.tuniu.common.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("city")
public class City implements Serializable {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private String name;
    /** 经度 */
    private String lng;
    /** 纬度 */
    private String lat;
}
