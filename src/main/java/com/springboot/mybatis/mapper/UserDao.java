package com.springboot.mybatis.mapper;

import com.springboot.mybatis.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

public interface UserDao {
    User selectByName(String name);
    User selectById(String id);
}
