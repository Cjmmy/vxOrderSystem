package com.cjmmy.vxordersystem.enums;

import lombok.Getter;


public enum  PayStatusEnums {
    WAIT(0,"等待支付"),
    SUCCESS(1,"支付成功")
    ;


    private int code;
    private String msg;

    PayStatusEnums(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
