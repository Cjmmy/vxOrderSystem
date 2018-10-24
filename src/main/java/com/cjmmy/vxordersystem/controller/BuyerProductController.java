package com.cjmmy.vxordersystem.controller;

import com.cjmmy.vxordersystem.service.impl.ProductCategoryServiceImpl;
import com.cjmmy.vxordersystem.service.impl.ProductInfoServiceImpl;
import com.cjmmy.vxordersystem.VO.ProductDetailVO;
import com.cjmmy.vxordersystem.VO.ProductInfoVO;
import com.cjmmy.vxordersystem.VO.ResultVO;
import com.cjmmy.vxordersystem.entity.ProductCategory;
import com.cjmmy.vxordersystem.entity.ProductInfo;
import com.cjmmy.vxordersystem.utils.ResultVOUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/buyer/product/")
public class BuyerProductController {
    @Autowired
    private ProductInfoServiceImpl productInfoService;
    @Autowired
    private ProductCategoryServiceImpl productCategoryService;
    @GetMapping("list")
    public ResultVO list() {
//        业务逻辑是查询到所有的商品，并将这些商品以商品类型进行分类，然后显示出来
//        查询所有商品
        List<ProductInfo> productInfoList = productInfoService.findAll();
//        遍历每一个商品，并将商品type值设置存入一个list准备作为参数传入findByCategoryTypeIn()
        List<Integer> typeList = new ArrayList<>();
        for (ProductInfo productInfo:productInfoList) {
            typeList.add(productInfo.getCategoryType());
        }
//        根据type查询所有商品类目
        List<ProductCategory> productCategoryList = productCategoryService.findByCategoryTypeIn(typeList);
//        遍历商品类目信息封装进ProductInfoVO
        List<ProductInfoVO> productInfoVOList = new ArrayList<>();
        for (ProductCategory productCategory : productCategoryList) {
            ProductInfoVO productInfoVO = new ProductInfoVO();
            productInfoVO.setCategoryName(productCategory.getCategoryName());
            productInfoVO.setCategoryType(productCategory.getCategoryType());
//            因为还要根据type分类，然后将相应的商品信息封装进data中
            List<ProductDetailVO> productInfoListForProductInfoVO = new ArrayList<>();
            for (ProductInfo productInfo:productInfoList) {
                if(productInfo.getCategoryType().equals(productCategory.getCategoryType())){
                    ProductDetailVO productDetailVO = new ProductDetailVO();
                    BeanUtils.copyProperties(productInfo,productDetailVO);
                    productInfoListForProductInfoVO.add(productDetailVO);
                }
            }
            productInfoVO.setProductDetailList(productInfoListForProductInfoVO);
            productInfoVOList.add(productInfoVO);
        }
        ResultVO resultVO = ResultVOUtil.success(productInfoVOList);
//        ProductDetailVO productDetailVO = new ProductDetailVO();
//        ProductInfoVO productInfoVO = new ProductInfoVO();
//        productInfoVO.setProductDetailList(Arrays.asList(productDetailVO));
//        ResultVO resultVO = new ResultVO();
//        resultVO.setCode(0);
//        resultVO.setMsg("成功");
//        resultVO.setData(Arrays.asList(productInfoVO));
        return resultVO;
    }
}
