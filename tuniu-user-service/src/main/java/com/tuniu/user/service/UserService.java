package com.tuniu.user.service;

import com.tuniu.common.domain.User;

public interface UserService {

    User findByAccount(String account);

    String register(User user);

    String getAccount(Integer id);

    boolean isNameAvailable(String name);
}
