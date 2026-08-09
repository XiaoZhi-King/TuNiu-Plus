package com.tuniu.order.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tuniu.common.domain.OrderDetail;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 订单明细 DAO（MyBatis-Plus：继承 BaseMapper）
 */
@Mapper
public interface OrderDetailMapper extends BaseMapper<OrderDetail> {

    @Insert("insert into order_detail(order_id, ticket_type, ticket_id, passenger_id, buy_num) " +
            "values(#{orderId}, #{ticketType}, #{ticketId}, #{passengerId}, #{buyNum})")
    int insert(OrderDetail detail);

    @Delete("delete from order_detail where order_id = #{orderId}")
    int deleteByOrderId(@Param("orderId") Integer orderId);

    /** 取订单关联的第一个 ticketId（一笔订单默认对应一个车次） */
    @Select("select ticket_id from order_detail where order_id = #{orderId} limit 1")
    Integer findTicketIdByOrderId(@Param("orderId") Integer orderId);

    /** 取订单关联的所有乘客 ID（用于订单详情乘车人回填） */
    @Select("select passenger_id from order_detail where order_id = #{orderId}")
    List<Integer> findPassengerIdsByOrderId(@Param("orderId") Integer orderId);
}
