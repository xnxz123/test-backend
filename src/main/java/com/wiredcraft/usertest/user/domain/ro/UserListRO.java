package com.wiredcraft.usertest.user.domain.ro;

import com.wiredcraft.usertest.common.vo.BaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName UserListRO
 * @Author lei
 * @Description 用户列表查询对象
 * @Date 10:35 2022/4/9
 * @Version 1.0
**/
@Data
@ApiModel("用户列表查询对象")
public class UserListRO extends BaseRequest {


    /**
     * user name
     */
    @ApiModelProperty("用户名")
    private String name;

    /**
     * user address
     */
    @ApiModelProperty("地址")
    private String address;

//    /**
//     * user description
//     */
//    private String description;
//
//
//    /**
//     * user longitude 经度
//     */
//    private Double longitude;
//
//
//    /**
//     * user latitude 纬度
//     */
//    private Double latitude;

}
