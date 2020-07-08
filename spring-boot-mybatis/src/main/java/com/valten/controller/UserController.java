package com.valten.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.valten.mapper.UserMapper;
import com.valten.pojo.User;
import com.valten.serive.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/userList")
    public String queryAll() {
        List<User> users = userService.queryAll();
        return JSON.toJSONString(users,
                SerializerFeature.WriteMapNullValue,
                SerializerFeature.WriteNullStringAsEmpty,
                SerializerFeature.WriteNullListAsEmpty,
                SerializerFeature.WriteDateUseDateFormat);
    }

    @RequestMapping("/user/{id}")
    public User queryById(@PathVariable("id") Integer id) {
        return userService.queryById(id);
    }

    @RequestMapping("/addUser")
    public String add() {
        User user = new User(8, "test", "testpd", "test@163.com", "Test", "2020-03-22 14:45:13");
        userService.add(user);
        return "addUser-ok";
    }

    @RequestMapping("/delUser/{id}")
    public String delete(@PathVariable("id") Integer id) {
        userService.delete(id);
        return "delUser-ok";
    }

    @RequestMapping("/updateUser/{id}")
    public String update(@PathVariable("id") Integer id) {
        User user = userService.queryById(id);
        user.setUsername("newUserName");
        user.setPassword("newPasword");
        user.setNickName("newNickName");
        userService.update(user);
        return "updateUser-ok";
    }
}
