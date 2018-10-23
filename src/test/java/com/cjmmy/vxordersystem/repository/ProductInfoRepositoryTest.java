package com.cjmmy.vxordersystem.repository;

import com.cjmmy.vxordersystem.entity.ProductInfo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;


@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductInfoRepositoryTest {
    @Autowired
    private ProductInfoRepository productInfoRepository;
    @Test
    public void saveTest(){
        ProductInfo productInfo = new ProductInfo();
        productInfo.setProductId("123");
        productInfo.setProductName("macbook pro");
        productInfo.setProductPrice(new BigDecimal(17000));
        productInfo.setProductStock(666);
        productInfo.setProductDescription("双十一降价，入手用五到六年");
        productInfo.setCategoryType(3);
        ProductInfo result = productInfoRepository.save(productInfo);
        Assert.assertNotEquals(null,result);
    }
}