package com.cjmmy.vxordersystem.repository;

import com.cjmmy.vxordersystem.entity.OrderMaster;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderMasterRepositoryTest {
    @Autowired
    private OrderMasterRepository orderMasterRepository;
    private final String OPENID = "110110";
    @Test
    public void saveTest(){
        OrderMaster orderMaster = new OrderMaster();
        orderMaster.setOrderId("11111111");
        orderMaster.setBuyerName("kk");
        orderMaster.setBuyerPhone("123456789123");
        orderMaster.setBuyerAddress("西工大");
        orderMaster.setBuyerOpenid(OPENID);
        orderMaster.setOrderAmount(new BigDecimal(2.5));

        OrderMaster result = orderMasterRepository.save(orderMaster);
        Assert.assertNotNull(result);
    }

    @Test
    public void findByBuyerOpenid() {
//        PageRequest是Pageable的实现类
        PageRequest pageRequest = PageRequest.of(0,2);
        Page<OrderMaster> result = orderMasterRepository.findByBuyerOpenid(OPENID,pageRequest);
        Assert.assertNotEquals(null,result);
    }
    @Test
    public void findAllTest(){
        List<OrderMaster> all = orderMasterRepository.findAll();
        Assert.assertNotEquals(0,all.size());
    }
}