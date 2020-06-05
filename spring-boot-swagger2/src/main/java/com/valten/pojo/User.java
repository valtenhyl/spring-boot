package com.valten.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

//@Api(注释)
@ApiModel("用户实体类")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    // int类型不指定example会报错 Illegal DefaultValue null for parameter type integer
    @NotNull
    @ApiModelProperty(value = "用户id", example = "101")
    private Integer id;
    @NotNull
    @ApiModelProperty(value = "用户名", example = "张三")
    public String username;
    @NotNull
    @ApiModelProperty(value = "密码", example = "000000")
    public String password;
}
