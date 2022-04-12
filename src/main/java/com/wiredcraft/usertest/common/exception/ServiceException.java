package com.wiredcraft.usertest.common.exception;

import com.wiredcraft.usertest.common.enums.ResultCode;
import lombok.Data;

@Data
public class ServiceException extends RuntimeException {
    private ResultCode resultCode;

    public ServiceException(ResultCode resultCode) {
        super(resultCode.getMsg());
        this.resultCode = resultCode;

    }
}
