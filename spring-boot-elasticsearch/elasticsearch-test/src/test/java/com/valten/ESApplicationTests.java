package com.valten;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.valten.model.User;
import com.valten.repo.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SpringBootTest
class ESApplicationTests {

    @Autowired
    private UserRepository userRepository;

    // 新增
    @Test
    public void testSave() {
        User user = new User();
        user.setId("1");
        user.setName("测试");
        user.setAddress("北京市丰台区");
        user.setAge(18);
        user.setBirth(new Date());
        user.setSex('女');

        userRepository.save(user);
    }

    // 根据id删除
    @Test
    public void testDeleteById() {
        userRepository.deleteById("2");
    }

    // 删除对象
    @Test
    public void testDelete() {
        User user = userRepository.queryById("4");
        userRepository.delete(user);
    }

    // 局部更新
    @Test
    public void testUpdate() {
        User user = userRepository.queryById("1");
        user.setName("哈哈");
        userRepository.save(user);
    }

    // 批量新增
    @Test
    public void testSaveAll() {
        User user = new User();
        user.setId("2");
        user.setName("张三");
        user.setAddress("北京市西城区");
        user.setAge(28);
        user.setBirth(new Date());
        user.setSex('男');

        User user2 = new User();
        user2.setId("3");
        user2.setName("李四");
        user2.setAddress("北京市东城区");
        user2.setAge(20);
        user.setBirth(new Date());
        user2.setSex('男');

        List<User> list = new ArrayList<>();
        list.add(user);
        list.add(user2);
        userRepository.saveAll(list);
    }

    // 查询
    @Test
    public void testQuery() {
        User user = userRepository.queryById("1");
        System.out.println(JSON.toJSONString(user));
    }

    // 查询所有
    @Test
    public void testFindAll() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<User> users = userRepository.findAll(pageable);
        System.out.println(JSONArray.toJSONString(users));
    }
}
