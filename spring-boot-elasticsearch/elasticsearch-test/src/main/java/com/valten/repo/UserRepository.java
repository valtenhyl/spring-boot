package com.valten.repo;

import com.valten.model.User;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends ElasticsearchRepository<User, String> {

    // 根据id查找用户
    User queryById(String id);
}
