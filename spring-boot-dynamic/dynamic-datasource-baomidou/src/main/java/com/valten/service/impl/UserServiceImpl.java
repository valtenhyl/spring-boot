package com.valten.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.valten.dao.UserMapper;
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
    @DS("master")
    public List<User> listByMaster() {
        return userMapper.selectList(null);
    }

    @Override
    @DS("slave")
    public List<User> listBySlave() {
        return userMapper.selectList(null);
    }
}
