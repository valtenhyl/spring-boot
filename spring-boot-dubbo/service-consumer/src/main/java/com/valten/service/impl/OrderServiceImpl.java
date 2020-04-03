package com.valten.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.valten.model.UserAddress;
import com.valten.service.OrderService;
import com.valten.service.UserService;

import java.util.List;

/**
 * 将服务提供者注册到注册中心
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Reference //引用注册中心服务 POM坐标，可以定义路径相同的接口名
    private UserService userService;

    @Override
    public List<UserAddress> initOrder(String userId) {
        System.out.println("用户id " + userId);
        return userService.getAddressList(userId);
    }
}
