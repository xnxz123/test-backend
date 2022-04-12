package com.wiredcraft.usertest.user.domain.ro;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @ClassName UserLoginRO
 * @Author lei 
 * @Description 用户登录对象
 * @Date 10:35 2022/4/9
 * @Version 1.0
**/
@Data
@ApiModel("用户登录对象")
public class UserLoginRO {

    /**
     * user name
     */
    @NotBlank(message = "name must not be empty")
    @ApiModelProperty("用户名")
    private String name;

    /**
     * user password
     */
    @NotBlank(message = "pwd must not be empty")
    @ApiModelProperty("密码")
    private String pwd;

}
