package com.valten.service;

import com.valten.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {

    // 保存用户
    void save(User user);

    // 批量保存用户
    void saveAll(List<User> users);

    // 根据id删除用户
    void deleteById(String id);

    // 删除用户
    void delete(User user);

    // 返回所有数据集合(分页)
    Page<User> findAll(Pageable pageable);

    // 根据id查询用户信息
    User queryById(String id);
}
