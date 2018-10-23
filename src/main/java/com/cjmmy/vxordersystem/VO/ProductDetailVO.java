package com.cjmmy.vxordersystem.VO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductDetailVO {
    @JsonProperty("id")
    private String productId;
    @JsonProperty("name")
    private String productName; //因为对应到json中有两个name，所以我们在这里详细说明是productName还是categoryName
    @JsonProperty("price")
    private BigDecimal productPrice;
    @JsonProperty("description")
    private String productDescription;
    @JsonProperty("icon")
    private String productIcon;
}
