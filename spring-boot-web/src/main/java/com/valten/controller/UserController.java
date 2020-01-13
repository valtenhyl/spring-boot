package com.valten.controller;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.valten.model.User;
import com.valten.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @RequestMapping("/add")
    public Map<String, Object> add() {
        userRepository.save(new User("aa1", "aa@126.com", "aa", "aa123456", "2016-06-01 10:56:08"));
        userRepository.save(new User("bb2", "bb@126.com", "bb", "bb123456", "2017-10-01 10:56:08"));
        userRepository.save(new User("cc3", "cc@126.com", "cc", "cc123456", "2018-08-11 10:56:08"));

        List<User> userList = userRepository.findAll();
        Map<String, Object> map = new HashMap<>();
        map.put("total", userList.size());
        map.put("data", userList);
        return map;
    }

    @RequestMapping("/list")
    public List<User> list() {
        return userRepository.findAll();
    }

    @RequestMapping("/find/{userName}")
    public User findByUserName(@PathVariable("userName") String userName) {
        return userRepository.findByUserName(userName);
    }
}
