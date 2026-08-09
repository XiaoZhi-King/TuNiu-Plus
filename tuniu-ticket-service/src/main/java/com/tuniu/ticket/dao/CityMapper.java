package com.tuniu.ticket.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tuniu.common.domain.City;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 城市 DAO（MyBatis-Plus：继承 BaseMapper 获得 CRUD/Wrapper 能力）
 */
@Mapper
public interface CityMapper extends BaseMapper<City> {

    @Select("select * from city")
    List<City> list();

    @Select("select * from city where name = #{name} limit 1")
    City selectByName(@Param("name") String name);

    @Select("select * from city where id = #{id} limit 1")
    City selectById(@Param("id") Integer id);
}
