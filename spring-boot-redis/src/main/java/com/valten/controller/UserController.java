package com.valten.controller;

import com.valten.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RestController
public class UserController {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private RedisTemplate redisTemplate;

    @RequestMapping("/test")
    public String test() {
        stringRedisTemplate.opsForValue().set("aaa", "111");
        return stringRedisTemplate.opsForValue().get("aaa");
    }

    @RequestMapping("/testObj")
    public String testObj() throws Exception {
        User user = new User("aa@126.com", "aa", "aa123456", "aa", "2019-11-12 12:12:12");
        ValueOperations<String, User> operations = redisTemplate.opsForValue();
        operations.set("com.valten", user);
        operations.set("com.valten.f", user, 1, TimeUnit.SECONDS);
        System.out.println(redisTemplate.hasKey("com.valten.f"));
        Thread.sleep(1000);
        Boolean hasKey = redisTemplate.hasKey("com.valten.f");
        if (hasKey) {
            return "exists is true";
        } else {
            return "exists is false";
        }
    }

    @RequestMapping("/getUser")
    @Cacheable(value = "user-key")
    public User getUser() {
        User user = new User("aa@126.com", "aa", "aa123456", "aa", "2019-11-12 12:12:12");
        System.out.println("若下面没出现“无缓存的时候调用”字样且能打印出数据表示测试成功");
        return user;
    }
}
