package com.tuniu.common.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("train_tickets")
public class TrainTickets implements Serializable {

    @TableId(type = IdType.AUTO)
    private Integer id;

    @TableField("train_number")
    private String trainNumber;   // 车次
    @TableField("start_station")
    private String startStation;  // 出发站
    @TableField("end_station")
    private String endStation;    // 到达站
    @TableField("start_time")
    private String startTime;     // 出发时间
    @TableField("end_time")
    private String endTime;       // 到达时间
    @TableField("seat_type")
    private String seatType;      // 座位类型
    @TableField("seat_number")
    private String seatNumber;    // 座位号
    private Double price;         // 票价
    @TableField("available_seats")
    private Integer availableSeats; // 余票
    private String lasts;         // 运行时长
}
