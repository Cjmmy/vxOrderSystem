package com.cjmmy.vxordersystem.ProductCategoryService.impl;

import com.cjmmy.vxordersystem.ProductCategoryService.ProductInfoService;
import com.cjmmy.vxordersystem.dto.CartDTO;
import com.cjmmy.vxordersystem.entity.ProductInfo;
import com.cjmmy.vxordersystem.enums.ExceptionStatusEnums;
import com.cjmmy.vxordersystem.exception.SystemException;
import com.cjmmy.vxordersystem.repository.ProductInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductInfoServiceImpl implements ProductInfoService {
    @Autowired
    private ProductInfoRepository productInfoRepository;

    public ProductInfo findOne(String productId) {
        return productInfoRepository.findById(productId).get();
    }


//    通过商品状态查询所有满足商品状态的商品
    public List<ProductInfo> findByProductStatus(Integer productStatus) {
        return productInfoRepository.findByProductStatus(productStatus);
    }

    public Page<ProductInfo> findAll(Pageable pageable) {
        return productInfoRepository.findAll(pageable);
    }

    public ProductInfo save(ProductInfo productInfo) {
        return productInfoRepository.save(productInfo);
    }

    public List<ProductInfo> findAll() {
        return productInfoRepository.findAll();
    }

    @Transactional
    public void increaseStock(List<CartDTO> cartDTOList) {
        for (CartDTO cartDTO : cartDTOList) {
            ProductInfo productInfo = productInfoRepository.findById(cartDTO.getProductId()).get();
            if(productInfo == null) {
                throw new SystemException(ExceptionStatusEnums.PRODUCT_NOT_EXIT);
            }
            int stock = productInfo.getProductStock() + cartDTO.getProductQuantity();
            productInfo.setProductStock(stock);
            productInfoRepository.save(productInfo);
        }
    }

    /**
     * 该方法要操作数据库，所以需要事务回滚
     * @param cartDTOList
     */
    @Transactional
    public void decreaseStock(List<CartDTO> cartDTOList) {
        for (CartDTO cartDTO : cartDTOList) {
            ProductInfo productInfo = productInfoRepository.findById(cartDTO.getProductId()).get();
            if(productInfo==null){
                throw new SystemException(ExceptionStatusEnums.PRODUCT_NOT_EXIT);
            }
            int stock = productInfo.getProductStock() - cartDTO.getProductQuantity();
            if(stock<0){
                throw new SystemException(ExceptionStatusEnums.STOCK_NOT_ENOUGH);
            }
            productInfo.setProductStock(stock);
            productInfoRepository.save(productInfo);
        }
    }


}
