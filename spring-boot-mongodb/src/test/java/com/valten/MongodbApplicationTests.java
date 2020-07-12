package com.valten;

import com.valten.pojo.User;
import com.valten.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class MongodbApplicationTests {

    @Autowired
    private UserService userService;

    @Test
    public void save() {
        User user = new User(123L, "传智播客", 12, "男", "北京");
        userService.save(user);
    }

    @Test
    public void update() {
        User user = new User(123L, "黑马程序员", 12, "男", "广州");
        userService.update(user);
    }

    @Test
    public void deleteById() {
        userService.deleteById(123L);
    }

    @Test
    public void findById() {
        User user = userService.findById(123L);
        System.out.println(user);
    }

    @Test
    public void findAll() {
        List<User> userList = userService.findAll();
        if (userList != null && userList.size() > 0) {
            for (User user : userList) {
                System.out.println(user);
            }
        }
    }

    @Test
    public void findByName() {
        List<User> userList = userService.findByName("黑马");
        if (userList != null && userList.size() > 0) {
            for (User user : userList) {
                System.out.println(user);
            }
        }
    }
}
