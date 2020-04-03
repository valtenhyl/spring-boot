package com.valten.service;

import com.valten.pojo.User;

import java.util.List;

public interface UserService {

    List<User> listByMaster();

    List<User> listBySlave();
}
