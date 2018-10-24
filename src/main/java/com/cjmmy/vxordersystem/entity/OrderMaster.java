package com.cjmmy.vxordersystem.entity;

import com.cjmmy.vxordersystem.enums.OrderStatusEnums;
import com.cjmmy.vxordersystem.enums.PayStatusEnums;
import com.cjmmy.vxordersystem.utils.serializer.Date2LongSerializer;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
@DynamicUpdate //因为涉及到了更新时间
@JsonInclude(JsonInclude.Include.NON_NULL) //该注解用于将返回给前端的json数据中的null字段删除不显示，这个注解只在写了的类管用，全剧配置
public class OrderMaster {
    @Id
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
//    订单状态 ，有默认值，用枚举表示
    private int orderStatus = OrderStatusEnums.NEW.getCode();
//    支付状态，有默认值，用枚举表示
    private int payStatus = PayStatusEnums.WAIT.getCode();
//    订单创建时间
    @JsonSerialize(using = Date2LongSerializer.class)
    private Date createTime;
//    订单更新时间
    @JsonSerialize(using = Date2LongSerializer.class)
    private Date updateTime;
}
