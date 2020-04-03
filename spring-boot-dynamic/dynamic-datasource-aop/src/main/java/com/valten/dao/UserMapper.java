package com.valten.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.valten.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface UserMapper extends BaseMapper<User> {

}
