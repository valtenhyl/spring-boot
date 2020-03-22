package com.valten.controller;

import com.valten.mapper.UserMapper;
import com.valten.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserMapper userMapper;

    @RequestMapping("/userList")
    public List<User> queryAll() {
        return userMapper.queryAll();
    }

    @RequestMapping("/user/{id}")
    public User queryById(@PathVariable("id") Integer id) {
        return userMapper.queryById(id);
    }

    @RequestMapping("/addUser")
    public String add() {
        User user = new User(8, "test", "testpd", "test@163.com", "Test", "2020-03-22 14:45:13");
        userMapper.add(user);
        return "addUser-ok";
    }

    @RequestMapping("/delUser/{id}")
    public String delete(@PathVariable("id") Integer id) {
        userMapper.delete(id);
        return "delUser-ok";
    }

    @RequestMapping("/updateUser/{id}")
    public String update(@PathVariable("id") Integer id) {
        User user = userMapper.queryById(id);
        user.setUsername("newUserName");
        user.setPassword("newPasword");
        user.setNickName("newNickName");
        userMapper.update(user);
        return "updateUser-ok";
    }
}
