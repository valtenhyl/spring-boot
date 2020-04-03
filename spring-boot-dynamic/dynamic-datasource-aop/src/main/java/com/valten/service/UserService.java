package com.valten.service;

import com.valten.pojo.User;

import java.util.List;

public interface UserService {

    List<User> listBySlave1();

    List<User> listBySlave2();
}
