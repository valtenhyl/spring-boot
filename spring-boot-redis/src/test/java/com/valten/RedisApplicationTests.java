package com.valten;

import com.valten.pojo.User;
import com.valten.utils.RedisUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SpringBootTest
class RedisApplicationTests {

    @Autowired
    @Qualifier("redisTemplate")
    private RedisTemplate redisTemplate;

    @Autowired
    private RedisUtil redisUtil;

    // 存入对象
    @Test
    void testSave() {
        User user = new User();
        user.setId("2");
        user.setAddress("北京市西城区");
        user.setAge(20);
        user.setBirth(new Date());
        user.setName("小美");
        user.setSex('女');
        redisUtil.set("user", user);
        System.out.println(redisUtil.get("user"));
    }

    // 存入集合
    @Test
    void testSaveList() {
        User user = new User();
        user.setId("2");
        user.setAddress("北京市西城区");
        user.setAge(20);
        user.setBirth(new Date());
        user.setName("小美");
        user.setSex('女');

        User user2 = new User();
        user2.setId("3");
        user2.setAddress("北京市海淀区");
        user2.setAge(24);
        user2.setBirth(new Date());
        user2.setName("小亮");
        user2.setSex('男');

        List<User> users = new ArrayList<>();
        users.add(user);
        users.add(user2);
        redisUtil.lSet("users", users);
        System.out.println(redisUtil.get("users"));
    }

    // 测试过期
    @Test
    void testExpire() {
        User user = new User();
        user.setId("2");
        user.setAddress("北京市西城区");
        user.setAge(20);
        user.setBirth(new Date());
        user.setName("小美");
        user.setSex('女');
        redisUtil.set("test_expire", user, 30);

        Object obj = redisUtil.get("test_expire");
        System.out.println(obj);
    }

    // 获取对象
    @Test
    void testGet() {
        Object user = redisUtil.get("user");
        System.out.println(user);
    }

    // 获取集合
    @Test
    void testGetList() {
        List<Object> users = redisUtil.lGet("users", 0, 10);
        System.out.println(users);
    }

}
