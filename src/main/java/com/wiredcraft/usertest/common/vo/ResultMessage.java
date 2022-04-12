package com.wiredcraft.usertest.common.vo;

import com.wiredcraft.usertest.common.enums.ResultCode;
import lombok.Data;

@Data
public class ResultMessage {
    public int code;
    public String msg;
    public Object data;

    private ResultMessage(ResultCode resultCode) {
        this.code = resultCode.getCode();
        this.msg = resultCode.getMsg();
    }

    private ResultMessage(ResultCode resultCode, Object data) {
        this.code = resultCode.getCode();
        this.msg = resultCode.getMsg();
        this.data = data;
    }

    private ResultMessage() {
    }

    public static ResultMessage ok(Object data) {
        return new ResultMessage(ResultCode.SUCCES, data);
    }

    public static ResultMessage error(ResultCode resultCode) {
        return new ResultMessage(resultCode);
    }

    public static ResultMessage error(ResultCode resultCode, Object data) {
        return new ResultMessage(resultCode,data);
    }
}
