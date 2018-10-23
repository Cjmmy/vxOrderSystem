package com.cjmmy.vxordersystem.exception;

import com.cjmmy.vxordersystem.enums.ExceptionStatusEnums;
import lombok.Getter;

@Getter
public class SystemException extends RuntimeException{
    private Integer code;

    public SystemException(ExceptionStatusEnums enums) {
        super(enums.getMsg());
        this.code = enums.getCode();
    }

    public SystemException(Integer code, String defaultMessage) {
        super(defaultMessage);
        this.code = code;
    }
}
