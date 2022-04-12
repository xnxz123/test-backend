package com.wiredcraft.usertest.common.vo;

import lombok.Data;

@Data
public class FieldValidatorError {
    private String field;
    private String message;
}
