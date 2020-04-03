package com.valten.service;

import com.valten.model.UserAddress;

import java.util.List;

public interface UserService {

    List<UserAddress> getAddressList(String userId);
}
