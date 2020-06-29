package com.valten.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseModel {

    private Integer id;
    private String username;
    private String password;
    private String email;
    private String nickName;
    private String regTime;

}
