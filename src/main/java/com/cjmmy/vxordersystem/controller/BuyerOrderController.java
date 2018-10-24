package com.cjmmy.vxordersystem.controller;

import com.cjmmy.vxordersystem.ProductCategoryService.impl.OrderServiceImpl;
import com.cjmmy.vxordersystem.VO.ResultVO;
import com.cjmmy.vxordersystem.dto.OrderDTO;
import com.cjmmy.vxordersystem.enums.ExceptionStatusEnums;
import com.cjmmy.vxordersystem.exception.SystemException;
import com.cjmmy.vxordersystem.form.OrderForm;
import com.cjmmy.vxordersystem.utils.ResultVOUtil;
import com.cjmmy.vxordersystem.utils.TransferUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/buyer/order/")
public class BuyerOrderController {
    @Autowired
    private OrderServiceImpl orderService;

    /**
     * 创建订单
     * @param orderForm
     * @param bindingResult
     * @return
     */
    @PostMapping("create")
    public ResultVO<Map<String,String>> create(OrderForm orderForm, BindingResult bindingResult){
//        如果表单校验有错误，那么要抛出异常
        if(bindingResult.hasErrors()){
            log.error("【创建订单】参数不正确  orderForm={}"+orderForm);
            throw new SystemException(ExceptionStatusEnums.PARAM_ERROR.getCode(), bindingResult.getFieldError().getDefaultMessage());
        }
        OrderDTO orderDTO = TransferUtil.orderForm2OrderDTO(orderForm);
        if(CollectionUtils.isEmpty(orderDTO.getOrderDetailList())){
            throw new SystemException(ExceptionStatusEnums.CART_EMPTY_ERROR);
        }
        OrderDTO order = orderService.createOrder(orderDTO);
        Map<String, String> map = new HashMap<>();
        map.put("orderId", order.getOrderId());
        return ResultVOUtil.success(map);
    }

    /**
     * 根据微信号查询订单列表，分页查找
     * @param openid
     * @param page
     * @param size
     * @return
     */
    @GetMapping("list")
    public ResultVO<List<OrderDTO>> list(@RequestParam("openid") String openid,
                                         @RequestParam(value = "page",defaultValue = "0") Integer page,
                                         @RequestParam(value = "size", defaultValue = "10") Integer size){
        if(StringUtils.isEmpty(openid)){
//            这两句话的顺序不能颠倒
            log.error("【查询订单】失败，微信账号不能为空");
            throw new SystemException(ExceptionStatusEnums.PARAM_ERROR);
        }
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<OrderDTO> servicePage = orderService.findList(openid, pageRequest);
        return ResultVOUtil.success(servicePage.getContent());
    }

    /**
     * 根据微信号和订单号查询订单详情
     * @param openid
     * @param orderid
     * @return
     */
    @GetMapping("detail")
    public ResultVO<OrderDTO> detail(@RequestParam("openid") String openid,
                                     @RequestParam("orderId") String orderid){
        //TODO 这样查不安全 后续完成
        OrderDTO orderDTO = orderService.findOne(orderid);
        return ResultVOUtil.success(orderDTO);
    }

    /**
     * 取消订单
     * @param openid
     * @param orderId
     * @return
     */
    @PostMapping("cancel")
    public ResultVO cancel(@RequestParam("openid") String openid,
                           @RequestParam("orderId") String orderId){
        OrderDTO orderDTO = orderService.findOne(orderId);
        orderService.cancel(orderDTO);
        return ResultVOUtil.success();
    }
}
