package com.valten.service.impl;

import com.valten.service.IEmployeeRepository;
import com.valten.util.RepositoryName;
import com.valten.vo.EmployeeVo;
import org.springframework.stereotype.Component;
@Component
public class EmployeeRepositoryImpl extends BaseRepositoryImpl<EmployeeVo> implements IEmployeeRepository {
    //索引
    @RepositoryName("store")
    protected String index;

    //类型
    @RepositoryName("employee")
    protected String type;

    /**
     * 写一些特殊的方法
     */

}
