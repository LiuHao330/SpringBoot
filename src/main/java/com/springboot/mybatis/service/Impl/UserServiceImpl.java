package com.springboot.mybatis.service.Impl;

import com.springboot.mybatis.entity.User;
import com.springboot.mybatis.mapper.UserDao;
import com.springboot.mybatis.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService {
   @Autowired
   private UserDao userDao;
    @Override
    public User findByName(String name) {
        return userDao.selectByName(name);
    }

    @Override
    public User findById(String id) {
        return userDao.selectById(id);
    }
}
