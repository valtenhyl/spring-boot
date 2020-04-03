package com.valten.service.impl;

import com.valten.dao.UserMapper;
import com.valten.datasource.annotation.DataSource;
import com.valten.pojo.User;
import com.valten.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    @DataSource("slave1")
    public List<User> listBySlave1() {
        return userMapper.selectList(null);
    }

    @Override
    @DataSource("slave2")
    public List<User> listBySlave2() {
        return userMapper.selectList(null);
    }
}
