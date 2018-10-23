package com.cjmmy.vxordersystem.VO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class ProductInfoVO {
    @JsonProperty("name")  //该注解的意思是ProductInfoVo对象中成员变量productName变到json中成为name
    private String categoryName;
    @JsonProperty("type")
    private Integer categoryType;
    @JsonProperty("foods")
    private List<ProductDetailVO> productDetailList;
}
