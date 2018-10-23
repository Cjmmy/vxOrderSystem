package com.cjmmy.vxordersystem.utils;

import com.cjmmy.vxordersystem.dto.OrderDTO;
import com.cjmmy.vxordersystem.entity.OrderDetail;
import com.cjmmy.vxordersystem.entity.OrderMaster;
import com.cjmmy.vxordersystem.enums.ExceptionStatusEnums;
import com.cjmmy.vxordersystem.exception.SystemException;
import com.cjmmy.vxordersystem.form.OrderForm;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;
@Slf4j
public class TransferUtil {
    public static OrderDTO orderMaster2OrderDTO(OrderMaster orderMaster){
        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(orderMaster,orderDTO);
        return orderDTO;
    }
    public static List<OrderDTO> orderMasterList2OrderDTOList(List<OrderMaster> orderMasterList){
        List<OrderDTO> orderDTOList = new ArrayList<>();
        for (OrderMaster orderMaster : orderMasterList) {
            OrderDTO orderDTO = TransferUtil.orderMaster2OrderDTO(orderMaster);
            orderDTOList.add(orderDTO);
        }
        return orderDTOList;
    }
    public static OrderDTO orderForm2OrderDTO(OrderForm orderForm){
        Gson gson = new Gson();
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setBuyerName(orderForm.getName());
        orderDTO.setBuyerPhone(orderForm.getPhone());
        orderDTO.setBuyerAddress(orderForm.getAddress());
        orderDTO.setBuyerOpenid(orderForm.getOpenid());
        /**
         * 对于购物车 因为是json，我们用gson
         */
        List<OrderDetail> orderDetailList = new ArrayList<>();
        try {
            /**
             * gson的使用，要将json转list
             */
            orderDetailList = gson.fromJson(orderForm.getItems(), new TypeToken<List<OrderDetail>>() {
            }.getType());
        }catch (Exception e){
            log.error("【对象转换】错误 ,String={}"+orderForm.getItems());
            throw new SystemException(ExceptionStatusEnums.PAY_STATUS_ERROR);
        }
        orderDTO.setOrderDetailList(orderDetailList);
        return orderDTO;
    }
}
