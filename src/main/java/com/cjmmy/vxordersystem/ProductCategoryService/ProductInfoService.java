package com.cjmmy.vxordersystem.ProductCategoryService;

import com.cjmmy.vxordersystem.dto.CartDTO;
import com.cjmmy.vxordersystem.entity.ProductInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface ProductInfoService {
    ProductInfo findOne(String productId);
    //根据分页查找一页的所有商品信息
    List<ProductInfo> findByProductStatus(Integer productStatu);
//    根据分页查找页数内所有商品
    Page<ProductInfo> findAll(Pageable pageable);
    ProductInfo save(ProductInfo productInfo);
//    查找所有商品
    List<ProductInfo> findAll();
//    增加库存
    void increaseStock(List<CartDTO> cartDTOList);
//    减少库存
    void decreaseStock(List<CartDTO> cartDTOList);

}
