package com.valten.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

/**
 * 指定集合, 默认 user
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "valten_user")
public class User implements Serializable {
    private Long id;
    private String name;
    private Integer age;
    private String gender;
    private String address;
}