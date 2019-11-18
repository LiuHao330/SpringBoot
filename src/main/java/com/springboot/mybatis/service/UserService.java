package com.springboot.mybatis.service;

import com.springboot.mybatis.entity.User;

public interface UserService {
    User findByName(String name);
    User findById(String id);
}
