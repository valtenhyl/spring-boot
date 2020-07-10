package com.valten.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter@Getter@ToString
public class EmployeeVo {
    private Long id;
    private String name;
    private Integer age;
    private String about;
}
