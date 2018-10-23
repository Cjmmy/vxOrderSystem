package com.cjmmy.vxordersystem.ProductCategoryService.impl;

import com.cjmmy.vxordersystem.dto.OrderDTO;
import com.cjmmy.vxordersystem.entity.OrderDetail;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class OrderServiceImplTest {
    @Autowired
    private OrderServiceImpl orderService;

    private final String BUYER_OPENID = "1101110";


    @Test
    public void createOrder() {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setBuyerName("cjm");
        orderDTO.setBuyerAddress("河工大");
        orderDTO.setBuyerPhone("123456789012");
        orderDTO.setBuyerOpenid(BUYER_OPENID);

        //购物车
        List<OrderDetail> orderDetailList = new ArrayList<>();
        OrderDetail o1 = new OrderDetail();
        o1.setProductId("123");
        o1.setProductQuantity(1);

        OrderDetail o2 = new OrderDetail();
        o2.setProductId("345");
        o2.setProductQuantity(2);

        orderDetailList.add(o1);
        orderDetailList.add(o2);

        orderDTO.setOrderDetailList(orderDetailList);

        OrderDTO result = orderService.createOrder(orderDTO);
        log.info("【创建订单】result={}", result);
        Assert.assertNotNull(result);
    }
    @Test
    public void findOneTest(){
        OrderDTO result = orderService.findOne("1234567");
        log.info("【查询单个订单】 result={}"+result);
        Assert.assertNotEquals(null,result);
    }
    @Test
    public void findListTest(){
        PageRequest pageRequest = PageRequest.of(0,2);
        Page<OrderDTO> list = orderService.findList("1101110", pageRequest);
        log.info("【查询到的订单列表】 list={}"+list);
        Assert.assertNotEquals(0,list.getTotalElements());
    }
    @Test
    public void cancelTest(){
        OrderDTO orderDTO = orderService.findOne("1540037203998601938");
        OrderDTO cancelResult = orderService.cancel(orderDTO);
        Assert.assertNotEquals(null,cancelResult);
    }

     @Test
    public void finishTest(){
         OrderDTO orderDTO = orderService.findOne("1540037203998601938");
         OrderDTO finishResult = orderService.finish(orderDTO);
         Assert.assertNotEquals(null,finishResult);
     }
     @Test
     public void payTest(){
         OrderDTO orderDTO = orderService.findOne("1540037203998601938");
         OrderDTO payResult = orderService.pay(orderDTO);
         Assert.assertNotEquals(null,payResult);
     }
}