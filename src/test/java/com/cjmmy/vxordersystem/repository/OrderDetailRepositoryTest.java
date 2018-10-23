package com.cjmmy.vxordersystem.repository;

import com.cjmmy.vxordersystem.entity.OrderDetail;
import com.sun.org.apache.regexp.internal.RE;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderDetailRepositoryTest {
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Test
    public void saveTest(){
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setDetailId("1234567810");
        orderDetail.setOrderId("1111111");
        orderDetail.setProductIcon("http://xxxx.jpg");
        orderDetail.setProductId("2222222");
        orderDetail.setProductName("皮蛋粥");
        orderDetail.setProductPrice(new BigDecimal(2.2));
        orderDetail.setProductQuantity(3);
        orderDetailRepository.save(orderDetail);
    }

    @Test
    public void findByOrderIdTest() {
        List<OrderDetail> result = orderDetailRepository.findByOrderId("1111111");
        Assert.assertNotEquals(0,result.size());
    }
}