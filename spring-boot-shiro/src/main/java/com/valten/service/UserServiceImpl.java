package com.valten.service;

import com.valten.mapper.UserMapper;
import com.valten.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;
    @Override
    public User queryByName(String name) {
        return userMapper.queryByName(name);
    }
}
