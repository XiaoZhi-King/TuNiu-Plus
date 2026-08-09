package com.tuniu.order.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tuniu.common.domain.Orders;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 订单 DAO（MyBatis-Plus：继承 BaseMapper，自定义动态 SQL 见 resources/mapper/OrderMapper.xml）
 */
@Mapper
public interface OrderMapper extends BaseMapper<Orders> {

    /** 下单：插入订单主表，回写自增主键 */
    int insert(Orders order);

    /** 按订单 id 更新状态（用于支付回调把 "待支付" -> "已支付"） */
    int updateStatusById(@Param("id") Integer id, @Param("status") String status);

    /** 按订单列表查询：可按用户、状态过滤 */
    List<Orders> list(@Param("userId") Integer userId, @Param("status") String status);

    /** 删除订单（物理删除） */
    int deleteById(@Param("id") Integer id);

    /** 按主键查询订单 */
    Orders findById(@Param("id") Integer id);
}
