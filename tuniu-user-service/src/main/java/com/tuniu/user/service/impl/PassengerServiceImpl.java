package com.tuniu.user.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tuniu.common.domain.Passenger;
import com.tuniu.user.dao.PassengerMapper;
import com.tuniu.user.service.PassengerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 乘车人服务（练习：请自行实现以下能力）
 * <p>
 * TODO 实现要求：
 * 1) list 分页：
 *      - 用 MP 构造 Page<Passenger>(pageNum, pageSize)
 *      - 组装 LambdaQueryWrapper：eq(userId != null, Passenger::getUserId, userId) + like(name 非空, Passenger::getName, name)
 *      - 返回 passengerMapper.selectPage(page, wrapper)
 * 2) deleteById：调用 passengerMapper.deleteById(id)
 * 3) save：passenger 为空返回 0；getId() != null 时 updateById，否则 insert
 * 4) listByIds：ids 为空返回 Collections.emptyList()；否则 passengerMapper.listByIds(ids)
 * <p>
 * 提示：MP 分页插件已在 user-service 的 MybatisPlusConfig 中注册
 */
@Service
@Slf4j
public class PassengerServiceImpl implements PassengerService {

    @Autowired
    private PassengerMapper passengerMapper;

    @Override
    public IPage<Passenger> list(Integer pageNum, Integer pageSize, Integer userId, String name) {
        // TODO: 自行实现（MP Page + LambdaQueryWrapper）
        throw new UnsupportedOperationException("练习待实现：PassengerServiceImpl.list");
    }

    @Override
    public int deleteById(Integer id) {
        // TODO: 自行实现
        throw new UnsupportedOperationException("练习待实现：PassengerServiceImpl.deleteById");
    }

    @Override
    public int save(Passenger passenger) {
        // TODO: 自行实现（id 为空 insert，非空 update）
        throw new UnsupportedOperationException("练习待实现：PassengerServiceImpl.save");
    }

    @Override
    public List<Passenger> listByIds(List<Integer> ids) {
        // TODO: 自行实现（订单服务 Feign 调用）
        throw new UnsupportedOperationException("练习待实现：PassengerServiceImpl.listByIds");
    }
}
