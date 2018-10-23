package com.cjmmy.vxordersystem.dto;

import lombok.Data;

/**
 * 这个类是创建订单时前段传过来的，就两个字段
 */
@Data
public class CartDTO {
    private String productId;
    private Integer productQuantity;

    public CartDTO(String productId, Integer productQuantity) {
        this.productId = productId;
        this.productQuantity = productQuantity;
    }
}
