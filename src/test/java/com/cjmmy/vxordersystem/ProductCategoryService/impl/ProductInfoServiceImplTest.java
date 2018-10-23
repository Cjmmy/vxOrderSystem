package com.cjmmy.vxordersystem.ProductCategoryService.impl;

import com.cjmmy.vxordersystem.entity.ProductInfo;
import com.cjmmy.vxordersystem.enums.ProductStatusEnums;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductInfoServiceImplTest {
    @Autowired
    private ProductInfoServiceImpl productInfoService;
    @Test
    public void findone() {
        ProductInfo result = productInfoService.findOne("123");
       Assert.assertNotEquals(null,result);
    }

    @Test
    public void findAll() {
        //2.0以后，用PageRequest.of()来构造pageRequest对象
        PageRequest pageRequest = PageRequest.of(0,2);
        Page<ProductInfo> all = productInfoService.findAll(pageRequest);
        Assert.assertNotEquals(0,all.getTotalElements());
    }

    @Test
    public void findByProductStatus() {
        List<ProductInfo> result = productInfoService.findByProductStatus(0);
        Assert.assertNotEquals(0,result.size());
    }

    @Test
    public void save() {
        ProductInfo productInfo = new ProductInfo();
        productInfo.setProductId("345");
        productInfo.setProductName("iphone xs");
        productInfo.setProductPrice(new BigDecimal(8699));
        productInfo.setProductStock(999);
        productInfo.setProductDescription("快如闪电");
        productInfo.setCategoryType(3);
        productInfo.setProductStatus(ProductStatusEnums.UP.getCode());//因为状态可能多种，为了方便记忆与管理我们设置成为枚举
        ProductInfo result = productInfoService.save(productInfo);
        Assert.assertNotEquals(null,result);
    }
}