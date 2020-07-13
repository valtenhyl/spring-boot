package com.valten.controller;

import com.alibaba.fastjson.JSONArray;
import com.valten.model.User;
import com.valten.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("find/page")
    public String findByPage(
            @RequestParam(value = "pageIndex", required = false, defaultValue = "0") int pageIndex,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize) {
        Pageable pageable = PageRequest.of(pageIndex, pageSize);
        Page<User> users = userService.findAll(pageable);
        return JSONArray.toJSONString(users);
    }

    @RequestMapping("find/{id}")
    public String findById(@PathVariable("id") String id) {
        User user = userService.queryById(id);
        return JSONArray.toJSONString(user);
    }

    @RequestMapping("save")
    public String save() {
        User user = new User();
        user.setId("1");
        user.setName("测试");
        user.setAddress("北京市丰台区");
        user.setAge(18);
        user.setBirth(new Date());
        user.setSex('女');

        userService.save(user);
        return "success";
    }

    @RequestMapping("batchSave")
    public String batchSave() {
        User user = new User();
        user.setId("2");
        user.setName("张三");
        user.setAddress("北京市西城区");
        user.setAge(28);
        user.setBirth(new Date());
        user.setSex('男');

        User user2 = new User();
        user2.setId("3");
        user2.setName("李四");
        user2.setAddress("北京市东城区");
        user2.setAge(20);
        user2.setBirth(new Date());
        user2.setSex('男');

        List<User> list = new ArrayList<>();
        list.add(user);
        list.add(user2);
        userService.saveAll(list);
        return "success";
    }

    @RequestMapping("delete/{id}")
    public String deleteById(@PathVariable("id") String id) {
        userService.deleteById(id);
        return "success";
    }

    @RequestMapping("delete")
    public String delete() {
        User user = userService.queryById("4");
        userService.delete(user);
        return "success";
    }

    @RequestMapping("update")
    public String update() {
        User user = userService.queryById("1");
        user.setName("哈哈");
        userService.save(user);
        return "success";
    }
}
