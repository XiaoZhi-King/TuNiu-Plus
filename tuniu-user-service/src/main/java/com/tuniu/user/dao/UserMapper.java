package com.tuniu.user.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tuniu.common.domain.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 用户 DAO（MyBatis-Plus：继承 BaseMapper 获得 CRUD/Wrapper 能力）
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

    @Select("select * from `user` where account = #{account}")
    User findByAccount(@Param("account") String account);

    @Insert("insert into `user`(account, password, name) values(#{account}, #{password}, #{name})")
    int register(User user);

    @Select("select account from `user` where id = #{id}")
    String getAccount(@Param("id") Integer id);

    @Select("select count(1) from `user` where name = #{name}")
    int countByName(@Param("name") String name);
}
