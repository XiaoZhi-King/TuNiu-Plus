package com.tuniu.ticket.service.impl;

import com.tuniu.common.domain.City;
import com.tuniu.ticket.dao.CityMapper;
import com.tuniu.ticket.service.CityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 城市服务（练习：请自行实现以下能力）
 * <p>
 * TODO 实现要求（这是 Redis 缓存的练手点）：
 * 1) list：
 *      - 先读 Redis key "ticket:city:list"（StringRedisTemplate.opsForValue().get）
 *      - 命中：用 ObjectMapper 反序列化为 List<City> 返回（注意捕获异常，失败回源 DB）
 *      - 未命中：cityMapper.list() 查 DB；查到后 ObjectMapper.writeValueAsString + redis set 60 min
 * 2) selectByName：key = "ticket:city:name:" + name，同样缓存穿透到 DB
 * 3) selectById：key = "ticket:city:id:" + id，同样缓存穿透到 DB
 * 4) 写缓存失败不抛异常（捕获后只 warn），保证 DB 查询可用即可（缓存降级策略）
 */
@Service
@Slf4j
public class CityServiceImpl implements CityService {

    @Autowired
    private CityMapper cityMapper;

    @Override
    public List<City> list() {
        // TODO: 自行实现（Redis 缓存 + DB 回源）
        throw new UnsupportedOperationException("练习待实现：CityServiceImpl.list");
    }

    @Override
    public City selectByName(String name) {
        // TODO: 自行实现（Redis 缓存 + DB 回源）
        throw new UnsupportedOperationException("练习待实现：CityServiceImpl.selectByName");
    }

    @Override
    public City selectById(Integer id) {
        // TODO: 自行实现（Redis 缓存 + DB 回源）
        throw new UnsupportedOperationException("练习待实现：CityServiceImpl.selectById");
    }
}
