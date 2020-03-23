package com.valten.dao;

import com.valten.pojo.User;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Repository
public class UserDao {

    private static Map<Integer, User> users;

    static {
        users = new HashMap<>();

        users.put(101, new User(101, "张三", "123456"));
        users.put(102, new User(102, "李四", "123456"));
        users.put(103, new User(103, "王五", "123456"));
        users.put(104, new User(104, "赵六", "123456"));
        users.put(105, new User(105, "田七", "123456"));
    }

    private static Integer initId = 106;

    // 获得所有用户信息
    public Collection<User> getUsers() {
        return users.values();
    }

    // 根据用户id获取用户信息
    public User getuserById(Integer id) {
        return users.get(id);
    }

    // 新增或修改用户信息
    public void addUser(User user) {
        if (user.getId() == null) {
            user.setId(initId++);
        }

        users.put(user.getId(), user);
    }

    // 根据用户id删除用户信息
    public void delUser(Integer id) {
        users.remove(id);
    }
}
