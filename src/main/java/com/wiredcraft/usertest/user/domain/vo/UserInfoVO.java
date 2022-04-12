package com.wiredcraft.usertest.user.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel("用户VO")
public class UserInfoVO {
    @ApiModelProperty("id")
    private Long id;

    /**
     * user name
     */
    @ApiModelProperty("用户名")
    private String name;


    /**
     * user date of birth
     */
    @ApiModelProperty("生日")
    private Date dob;

    /**
     * user address
     */
    @ApiModelProperty("住址")
    private String address;

    /**
     * user description
     */
    @ApiModelProperty("描述")
    private String description;


    /**
     * user longitude 经度
     */
    @ApiModelProperty("经度")
    private Double longitude;


    /**
     * user latitude 纬度
     */
    @ApiModelProperty("纬度")
    private Double latitude;

}
