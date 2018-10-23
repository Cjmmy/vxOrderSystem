package com.cjmmy.vxordersystem.dto;

import com.cjmmy.vxordersystem.entity.OrderDetail;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 这个类的作用是专门用来传输数据的，因为我们的订单最好携带多个单一商品订单的详细信息列表，但是我们如果将List<OrderDetail> orderDetailList直接放入
 * 数据映射的实体类内，会报错会污染代码，所以我们把它单独拿出来作为传输数据的一个类
 */
@Data
public class OrderDTO {
    private String orderId;
    //    买家姓名
    private String buyerName;
    //    买家电话
    private String buyerPhone;
    //    买家地址
    private String buyerAddress;
    //    买家微信号
    private String buyerOpenid;
    //    订单金额
    private BigDecimal orderAmount;
    //    订单状态
    private Integer orderStatus;
    //    支付状态
    private Integer payStatus;
    //    订单创建时间
    private Date createTime;
    //    订单更新时间
    private Date updateTime;
//    单个商品订单的详细内容list
    private List<OrderDetail> orderDetailList;
}
