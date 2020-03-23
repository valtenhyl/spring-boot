package com.valten.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//@Api(注释)
@ApiModel("用户实体类")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    // int类型不指定example会报错 Illegal DefaultValue null for parameter type integer
    @ApiModelProperty(value = "用户id", example = "101")
    private Integer id;
    @ApiModelProperty("用户名")
    public String username;
    @ApiModelProperty("密码")
    public String password;
}
