package com.cjmmy.vxordersystem.ProductCategoryService;

import com.cjmmy.vxordersystem.dto.OrderDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface OrderService {
    //创建订单
    OrderDTO createOrder(OrderDTO orderDTO);
    //根据订单id查询一个订单
    OrderDTO findOne(String orderId);
    //根据微信号和分页查询订单列表
    Page<OrderDTO> findList(String buyerOpenId, Pageable pageable);
    //取消订单,返回修改了订单状态的订单，其实就是从NEW变为CANCEL
    OrderDTO cancel(OrderDTO orderDTO);
    OrderDTO finish(OrderDTO orderDTO);
    OrderDTO pay(OrderDTO orderDTO);
}
