package com.cjmmy.vxordersystem.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * form包里面都是表单
 */
@Data
public class OrderForm {
    @NotEmpty(message = "姓名不能为空")
    private String name;
    @NotEmpty(message = "电话不能为空")
    private String phone;
    @NotEmpty(message = "地址不能为空")
    private String address;
    @NotEmpty(message = "微信账号不能为空")
    private String openid;
    /**
     * 购物车以json格式显示的，所以是String
     */
    @NotEmpty(message = "购物车不能为空")
    private String items;
}
