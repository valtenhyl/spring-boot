package com.valten.serive;

import com.valten.pojo.User;

import java.util.List;

public interface UserService {

    List<User> queryAll();

    User queryById(Integer id);

    void add(User user);

    void delete(Integer id);

    void update(User user);
}
