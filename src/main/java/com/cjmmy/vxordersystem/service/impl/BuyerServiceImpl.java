package com.cjmmy.vxordersystem.service.impl;

import com.cjmmy.vxordersystem.dto.OrderDTO;
import com.cjmmy.vxordersystem.enums.ExceptionStatusEnums;
import com.cjmmy.vxordersystem.exception.SystemException;
import com.cjmmy.vxordersystem.service.BuyerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Slf4j
@Service
public class BuyerServiceImpl implements BuyerService {
    @Autowired
    private OrderServiceImpl orderService;
    @Override
    public OrderDTO findOrderOne(String openid, String orderId) {
        OrderDTO orderDTO = checkIsLegal(openid, orderId);
        return orderDTO;
    }

    @Override
    public void cancelOrder(String openid, String orderId) {
        OrderDTO orderDTO = checkIsLegal(openid, orderId);
        orderService.cancel(orderDTO);
    }

    /**
     * 前端只传来openid和orderId进行订单的查询和删除，我们首先验证合法性，通过orderId查到OrderDTO，然后根据查到的OrderDTO中
     * 微信号与传来的微信号对比以验证用户的合法性，合法则将订单信息返回
     * @param openId
     * @param orderId
     * @return
     */
    public OrderDTO checkIsLegal(String openId, String orderId){
        OrderDTO serviceOne = orderService.findOne(orderId);
        if (serviceOne==null){
            return null;
        }
        if (!serviceOne.getBuyerOpenid().equals(openId)){
            log.error("【账户认证】失败，微信账户不对");
            throw new SystemException(ExceptionStatusEnums.BUYERID_ERROR);
        }
        return serviceOne;
    }
}
