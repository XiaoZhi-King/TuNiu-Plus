package com.tuniu.ticket.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tuniu.common.domain.TrainTickets;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * 车票 DAO（MyBatis-Plus：继承 BaseMapper，自定义 XML 方法保留）
 */
@Mapper
public interface TrainTicketsMapper extends BaseMapper<TrainTickets> {

    /**
     * 按出发/到达站点 + 日期 + 排序条件查询车票列表
     * 动态 SQL 见 resources/mapper/TrainTicketsMapper.xml
     */
    List<TrainTickets> list(@Param("startStation") String startStation,
                            @Param("endStation") String endStation,
                            @Param("startTime") String startTime,
                            @Param("condition") String condition);

    /**
     * 查询全部车票（用于把库存预热到 Redis）
     */
    @Select("select * from train_tickets")
    List<TrainTickets> importAll();

    /**
     * 按主键查询
     */
    @Select("select * from train_tickets where id = #{id}")
    TrainTickets findById(@Param("id") Integer id);

    /**
     * 库存原子扣减（数据库层防超卖兜底）
     */
    @Update("update train_tickets set available_seats = available_seats - 1 where id = #{id} and available_seats > 0")
    int decrementAvailableSeats(@Param("id") Integer id);
}
