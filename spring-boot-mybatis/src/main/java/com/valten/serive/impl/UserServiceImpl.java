package com.valten.serive.impl;

import com.valten.mapper.UserMapper;
import com.valten.pojo.User;
import com.valten.serive.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<User> queryAll() {
        return userMapper.queryAll();
    }

    @Override
    public User queryById(Integer id) {
        return userMapper.queryById(id);
    }

    @Transactional(rollbackFor = {RuntimeException.class})
    @Override
    public void add(User user) {
        userMapper.add(user);
        throw new RuntimeException("添加用户发生异常");
    }

    @Override
    public void delete(Integer id) {
        userMapper.delete(id);
    }

    @Override
    public void update(User user) {
        userMapper.update(user);
    }
}
