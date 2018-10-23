package com.cjmmy.vxordersystem.utils;

import com.cjmmy.vxordersystem.dto.OrderDTO;
import com.cjmmy.vxordersystem.entity.OrderMaster;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

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
}
