package com.valten.service;

import com.valten.pojo.User;

public interface UserService {
    User queryByName(String name);
}
