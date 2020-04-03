package com.valten;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.valten.pojo.User;
import com.valten.utils.RedisUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
class RedisApplicationTests {

    @Autowired
    @Qualifier("redisTemplate")
    private RedisTemplate redisTemplate;

    @Autowired
    private RedisUtil redisUtil;

    @Test
    void test1() {

        User user = new User("张三", 19);
        // String userJson = new ObjectMapper().writeValueAsString(user);
        redisTemplate.opsForValue().set("user", user);
        System.out.println(redisTemplate.opsForValue().get("user"));
    }

    @Test
    void test2() {

        User user = new User("张三", 19);
        redisUtil.set("user", user);
        System.out.println(redisUtil.get("user"));
    }

}
