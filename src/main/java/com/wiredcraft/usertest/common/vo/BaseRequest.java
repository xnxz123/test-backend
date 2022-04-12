package com.wiredcraft.usertest.common.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class BaseRequest {
    @ApiModelProperty("页数")
    private Integer pageNum;
    @ApiModelProperty("页长")
    private Integer pageSize;
    @ApiModelProperty("排序字段")
    private String orderBy;
    @ApiModelProperty("是否升序")
    private Boolean asc = false;
}
