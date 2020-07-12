package com.valten.service;

import com.valten.support.UserAddress;

import java.util.List;

public interface UserService {

    List<UserAddress> getAddressList(String userId);
}
