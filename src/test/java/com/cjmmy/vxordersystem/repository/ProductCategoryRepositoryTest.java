package com.cjmmy.vxordersystem.repository;

import com.cjmmy.vxordersystem.entity.ProductCategory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductCategoryRepositoryTest {
    @Autowired
    private ProductCategoryRepository productCategoryRepository;
    @Test
//    @Transactional //如果不想测试数据出现在数据库中污染数据库，我们设置回滚，测试完全部回滚
    public void addOne() {
        ProductCategory productCategory = new ProductCategory("美食饮品", 1);
        productCategoryRepository.save(productCategory);
    }



    @Test
    public void findOneTest() {
        ProductCategory productCategory = productCategoryRepository.findById(1).get();
        System.out.println(productCategory.toString());
    }

    @Test
    public void updateInfo() {
        ProductCategory productCategory = productCategoryRepository.findById(1).get();
        productCategory.setCategoryType(3);
        productCategoryRepository.save(productCategory);
    }
    @Test
    public void findByCategoryType(){
        List<Integer> list = Arrays.asList(1,2,3);
        List<ProductCategory> categoryTypeList = productCategoryRepository.findByCategoryTypeIn(list);
        Assert.assertEquals(0, categoryTypeList.size());
    }
}