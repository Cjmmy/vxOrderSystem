package com.cjmmy.vxordersystem.service;

import com.cjmmy.vxordersystem.dto.OrderDTO;

/**
 * 因为orderService中的取消订单和查看订单细节都应该验证微信号是不是本人进行的操作
 * 而在那里写业务逻辑过于复杂，所以我们把之提取出来
 */
public interface BuyerService {
    OrderDTO findOrderOne(String openid, String orderId);
    void cancelOrder(String openid, String orderId);
}
