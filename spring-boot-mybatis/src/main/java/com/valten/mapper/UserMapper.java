package com.valten.mapper;

import com.valten.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

// 这个注解表示这是一个mybatis的mapper类
@Mapper
@Repository
public interface UserMapper {

    List<User> queryAll();

    User queryById(Integer id);

    void add(User user);

    void delete(Integer id);

    void update(User user);
}
