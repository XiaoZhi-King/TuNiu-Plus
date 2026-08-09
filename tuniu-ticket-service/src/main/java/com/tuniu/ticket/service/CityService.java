package com.tuniu.ticket.service;

import com.tuniu.common.domain.City;

import java.util.List;

/**
 * 城市服务：城市数据访问，Redis 缓存加速
 */
public interface CityService {

    /**
     * 全部城市列表（走缓存）
     */
    List<City> list();

    /**
     * 按城市名查询
     */
    City selectByName(String name);

    /**
     * 按主键查询
     */
    City selectById(Integer id);
}
