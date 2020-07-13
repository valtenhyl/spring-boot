package com.valten.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {

    private String id;

    private String name;

    private Character sex;

    private Integer age;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date birth;

    private String address;
}
