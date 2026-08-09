package com.tuniu.user.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tuniu.common.domain.Passenger;

import java.util.List;

public interface PassengerService {

    /** 分页查询（MyBatis-Plus IPage，字段与 PageInfo 兼容） */
    IPage<Passenger> list(Integer pageNum, Integer pageSize, Integer userId, String name);

    int deleteById(Integer id);

    int save(Passenger passenger);

    /** 按 ID 集合批量查询（供订单服务 Feign 调用） */
    List<Passenger> listByIds(List<Integer> ids);
}
