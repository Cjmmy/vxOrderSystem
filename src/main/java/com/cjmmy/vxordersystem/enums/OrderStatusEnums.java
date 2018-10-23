package com.cjmmy.vxordersystem.enums;

import lombok.Getter;


public enum OrderStatusEnums {
    NEW(0,"新订单"),
    FINISHED(1,"完成"),
    CANCEL(2,"已取消"),
    ;

    private int code;
    private String msg;

    OrderStatusEnums(int code, String msg) {
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
