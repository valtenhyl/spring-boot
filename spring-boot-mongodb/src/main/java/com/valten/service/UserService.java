package com.valten.service;

import com.valten.pojo.User;

import java.util.List;

public interface UserService {
    void save(User user);

    void update(User user);

    void deleteById(Long id);

    User findById(Long id);

    List<User> findAll();

    List<User> findByName(String name);
}