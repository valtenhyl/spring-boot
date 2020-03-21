package com.valten.dao;


import com.valten.pojo.Department;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

// 部门dao
@Repository
public class DepartmentDao {

    // 模拟数据库中的数据

    private static Map<Integer, Department> departments;

    static {
        departments = new HashMap<>();

        departments.put(101, new Department(101, "总裁办"));
        departments.put(102, new Department(102, "行政中心"));
        departments.put(103, new Department(103, "产品中心"));
        departments.put(104, new Department(104, "研发中心"));
        departments.put(105, new Department(105, "销售中心"));
    }

    // 获得所有部门信息
    public Collection<Department> getDepartments() {
        return departments.values();
    }

    // 根据部门id获取部门信息
    public Department getDepartmentById(Integer deptId) {
        return departments.get(deptId);
    }

}
