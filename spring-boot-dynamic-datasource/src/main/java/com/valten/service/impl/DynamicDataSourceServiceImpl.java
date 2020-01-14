package com.valten.service.impl;

import com.valten.dao.SysUserDao;
import com.valten.datasource.annotation.DataSource;
import com.valten.entity.SysUserEntity;
import com.valten.service.DynamicDataSourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("dynamicDataSourceService")
public class DynamicDataSourceServiceImpl implements DynamicDataSourceService {

    @Autowired
    private SysUserDao sysUserDao;

    @Override
    @Transactional
    public void updateUser(Long id){
        SysUserEntity user = new SysUserEntity();
        user.setUserId(id);
        user.setMobile("13500000000");
        sysUserDao.updateById(user);
    }

    @Override
    @Transactional
    @DataSource("slave1")
    public void updateUserBySlave1(Long id){
        SysUserEntity user = new SysUserEntity();
        user.setUserId(id);
        user.setMobile("13500000001");
        sysUserDao.updateById(user);
    }

    @Override
    @DataSource("slave2")
    @Transactional
    public void updateUserBySlave2(Long id){
        SysUserEntity user = new SysUserEntity();
        user.setUserId(id);
        user.setMobile("13500000002");
        sysUserDao.updateById(user);
//
//        //测试事物
//        int i = 1/0;
    }
}
