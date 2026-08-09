package com.tuniu.user.service.impl;

import com.tuniu.common.domain.User;
import com.tuniu.user.dao.UserMapper;
import com.tuniu.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 用户服务（练习：请自行实现以下能力）
 * <p>
 * TODO 实现要求：
 * 1) findByAccount：校验 account 非空后，调用 userMapper.findByAccount(account) 返回 User（用于登录查用户）
 * 2) register：
 *      - 校验入参非空（name/password 必传），不满足抛 IllegalArgumentException
 *      - 生成账号：yyyyMMdd + 自增序号（例如用 AtomicInteger，需考虑并发安全）
 *      - 使用 SaltMD5Util.generateSaltPassword(password) 对密码加盐加密
 *      - 调用 userMapper.register(user) 插入，rows != 1 抛 RuntimeException("注册失败")
 *      - 返回生成的账号
 * 3) getAccount：直接调用 userMapper.getAccount(id)
 * 4) isNameAvailable：name 为空返回 false；否则 userMapper.countByName(name) == 0 表示可用
 * <p>
 * 参考工具类：com.tuniu.common.util.SaltMD5Util
 */
@Service
@Transactional
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User findByAccount(String account) {
        // TODO: 自行实现
        throw new UnsupportedOperationException("练习待实现：UserServiceImpl.findByAccount");
    }

    @Override
    public String register(User user) {
        // TODO: 自行实现（账号生成 + SaltMD5 加密 + 落库）
        throw new UnsupportedOperationException("练习待实现：UserServiceImpl.register");
    }

    @Override
    public String getAccount(Integer id) {
        // TODO: 自行实现
        throw new UnsupportedOperationException("练习待实现：UserServiceImpl.getAccount");
    }

    @Override
    public boolean isNameAvailable(String name) {
        // TODO: 自行实现（姓名重复校验）
        throw new UnsupportedOperationException("练习待实现：UserServiceImpl.isNameAvailable");
    }
}
