package com.tuniu.user.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tuniu.common.domain.Passenger;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 乘车人 DAO（MyBatis-Plus：继承 BaseMapper 获得 CRUD/Wrapper 能力）
 */
@Mapper
public interface PassengerMapper extends BaseMapper<Passenger> {

    @Select("select * from passenger where user_id = #{userId} and name like concat('%', #{name}, '%')")
    List<Passenger> list(@Param("userId") Integer userId, @Param("name") String name);

    @Delete("delete from passenger where id = #{id}")
    int deleteById(Integer id);

    @Insert("insert into passenger(name,type,id_card,phone,user_id,card_type,country,gender,often)" +
            "values(#{name},#{type},#{idCard},#{phone},#{userId},#{cardType},#{country},#{gender},#{often})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Passenger passenger);

    @Update("update passenger set " +
            "name=#{name},type=#{type},id_card=#{idCard},phone=#{phone},user_id=#{userId}," +
            "card_type=#{cardType},country=#{country},gender=#{gender},often=#{often} where id=#{id}")
    int update(Passenger passenger);

    @Select("select * from passenger where id = #{id}")
    Passenger findById(Integer id);

    /** 按 ID 集合批量查询（订单服务通过 Feign 调用，用于订单详情乘车人回填） */
    @Select({
            "<script>",
            "select * from passenger where id in",
            "<foreach collection='ids' item='id' open='(' separator=',' close=')'>",
            "#{id}",
            "</foreach>",
            "</script>"
    })
    List<Passenger> listByIds(@Param("ids") List<Integer> ids);
}
