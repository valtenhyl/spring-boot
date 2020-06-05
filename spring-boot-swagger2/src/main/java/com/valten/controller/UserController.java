package com.valten.controller;

import com.valten.dao.UserDao;
import com.valten.pojo.User;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;

@RestController
public class UserController {

    @Autowired
    private UserDao userDao;

    @ApiOperation(value = "用户列表查询", notes = "获取所有用户信息")
    @GetMapping("/user/list")
    public Collection<User> getUsers(){
        return userDao.getUsers();
    }

    // int类型不指定example会报错 Illegal DefaultValue null for parameter type integer
    @ApiOperation(value = "查询用户信息", notes = "根据用户id查询用户信息")
    @ApiImplicitParam(name = "id", value = "用户id", example = "101", required = true)
    @GetMapping("/user/{id}")
    public User getUserById(@PathVariable("id") Integer id){
        return userDao.getuserById(id);
    }

    @ApiOperation("新增用户信息")
    @PostMapping("/user/add")
    public void getUserById(@ApiParam(name = "User", value = "传入用户信息", required = true) @RequestBody @Valid User user){
        userDao.addUser(user);
    }

    @ApiOperation(value = "删除用户信息", notes = "根据用户id删除用户信息")
    @DeleteMapping("/user/{id}")
    public void delUser(@ApiParam(name = "id", value = "用户id", example = "101") @PathVariable("id") Integer id){
        userDao.delUser(id);
    }

    @ApiOperation("修改用户信息")
    @PatchMapping("/user/update")
    public void updateUser(User user){
        userDao.addUser(user);
    }
}
