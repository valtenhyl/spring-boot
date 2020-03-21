package com.valten.pojo;


import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

// 员工
@Data
@NoArgsConstructor
public class Employee {

    private Integer id;
    private String name;
    private String email;
    private Integer gender; // 性别 0 女 1 男
    private Department department;
    private Date birth;

    public Employee(Integer id, String name, String email, Integer gender, Department department) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.gender = gender;
        this.department = department;
        this.birth = new Date();
    }
}
