package com.valten.service;

import com.valten.support.UserAddress;

import java.util.List;

public interface OrderService {

    List<UserAddress> initOrder(String userId);
}
