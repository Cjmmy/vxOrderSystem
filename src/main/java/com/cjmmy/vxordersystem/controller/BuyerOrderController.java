package com.cjmmy.vxordersystem.controller;

import com.cjmmy.vxordersystem.ProductCategoryService.impl.OrderServiceImpl;
import com.cjmmy.vxordersystem.VO.ResultVO;
import com.cjmmy.vxordersystem.dto.OrderDTO;
import com.cjmmy.vxordersystem.enums.ExceptionStatusEnums;
import com.cjmmy.vxordersystem.exception.SystemException;
import com.cjmmy.vxordersystem.form.OrderForm;
import com.cjmmy.vxordersystem.utils.ResultVOUtil;
import com.cjmmy.vxordersystem.utils.TransferUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/buyer/order/")
public class BuyerOrderController {
    @Autowired
    private OrderServiceImpl orderService;
    @PostMapping("create")
    public ResultVO<Map<String,String>> create(OrderForm orderForm, BindingResult bindingResult){
//        如果表单校验有错误，那么要抛出异常
        if(bindingResult.hasErrors()){
            log.error("【创建订单】参数不正确  orderForm={}"+orderForm);
            throw new SystemException(ExceptionStatusEnums.PARAM_ERROR.getCode(), bindingResult.getFieldError().getDefaultMessage());
        }
        OrderDTO orderDTO = TransferUtil.orderForm2OrderDTO(orderForm);
        if(CollectionUtils.isEmpty(orderDTO.getOrderDetailList())){
            throw new SystemException(ExceptionStatusEnums.CART_EMPTY_ERROR);
        }
        OrderDTO order = orderService.createOrder(orderDTO);
        Map<String, String> map = new HashMap<>();
        map.put("orderId", order.getOrderId());
        return ResultVOUtil.success(map);
    }
}
