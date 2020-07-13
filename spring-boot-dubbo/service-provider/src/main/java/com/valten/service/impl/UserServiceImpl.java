package com.valten.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.valten.support.UserAddress;
import com.valten.service.UserService;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Service // 声明暴漏的服务
public class UserServiceImpl implements UserService {
    @Override
    public List<UserAddress> getAddressList(String userId) {
        Map<String, UserAddress> adddress = new HashMap<>();
        UserAddress address1 = new UserAddress(1, "北京市昌平区鸿福科技园综合楼3层", "1", "里根", "15336788981", "1");
        UserAddress address2 = new UserAddress(2, "深圳市南山区宏观科技园3区4号楼2层", "2", "锦瑟", "127878726547", "0");

        adddress.put("1", address1);
        adddress.put("2", address2);

        UserAddress userAddress = adddress.get(userId);
        return Collections.singletonList(userAddress);
    }













}
