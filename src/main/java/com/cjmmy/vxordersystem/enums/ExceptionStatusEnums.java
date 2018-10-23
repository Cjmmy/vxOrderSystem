package com.cjmmy.vxordersystem.enums;

import lombok.Getter;

@Getter
public enum ExceptionStatusEnums {
    PRODUCT_NOT_EXIT(44,"商品不存在"),
    STOCK_NOT_ENOUGH(40,"商品数量不足"),
    ORDER_NOT_EXIT(54,"查询订单不存在"),
    ORDER_STATUS_ERROR(50,"订单状态不正确"),
    ORDER_STATUS_FAIL(51,"订单状态存库失败"),
    PAY_STATUS_ERROR(64,"订单支付状态不正确"),
    PAY_STATUS_FAIL(60,"订单支付状态保存失败")
    ;
    private Integer code;
    private String msg;

    ExceptionStatusEnums(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
