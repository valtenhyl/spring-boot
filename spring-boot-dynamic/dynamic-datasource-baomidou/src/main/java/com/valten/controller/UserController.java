package com.valten.controller;

import com.valten.pojo.User;
import com.valten.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/list1")
    public List<User> list1() {
        return userService.listByMaster();
    }

    @RequestMapping("/list2")
    public List<User> list2() {
        return userService.listBySlave();
    }
}
