package com.cjmmy.vxordersystem.enums;

import lombok.Getter;

@Getter//枚举必须有getter方法
public enum ProductStatusEnums {

    UP(0,"上架"),
    DOWN(1,"下架")
    ;

    //    状态码
    private Integer code;
//    状态码信息
    private String message;

    ProductStatusEnums(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    ProductStatusEnums() {
    }
}
