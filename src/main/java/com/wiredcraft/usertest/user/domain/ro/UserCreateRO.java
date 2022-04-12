package com.wiredcraft.usertest.user.domain.ro;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @ClassName UserCreateRO
 * @Author lei
 * @Description 用户新增对象
 * @Date 10:35 2022/4/9
 * @Version 1.0
**/
@Data
@ApiModel("用户新增对象")
public class UserCreateRO {


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

    /**
     * user date of birth
     */
    @ApiModelProperty("生日")
    @NotNull(message = "user date of birth must not be empty")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date dob;

    /**
     * user address
     */
    @ApiModelProperty("地址")
    @NotBlank(message = "address must not be empty")
    private String address;

    /**
     * user description
     */
    @NotBlank(message = "description must not be empty")
    private String description;


    /**
     * user longitude 经度
     */
    @ApiModelProperty("经度")
    @NotNull(message = "longitude must not be empty")
    private Double longitude;


    /**
     * user latitude 纬度
     */
    @ApiModelProperty("纬度")
    @NotNull(message = "latitude must not be empty")
    private Double latitude;

}
