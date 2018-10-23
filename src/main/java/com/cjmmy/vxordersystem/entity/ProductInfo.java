package com.cjmmy.vxordersystem.entity;


import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

@Entity
@Data
public class ProductInfo {
    @Id
    private String productId;

    private String productName;

    private BigDecimal productPrice;
//    商品库存
    private Integer productStock;
//    商品描述
    private String productDescription;
//    商品小图
    private String productIcon;
//    商品所属类别
    private Integer categoryType;
//    商品状态
    private int productStatus;
    public ProductInfo() {
    }
}
