package com.cjmmy.vxordersystem.ProductCategoryService.impl;

import com.cjmmy.vxordersystem.ProductCategoryService.OrderService;
import com.cjmmy.vxordersystem.dto.CartDTO;
import com.cjmmy.vxordersystem.dto.OrderDTO;
import com.cjmmy.vxordersystem.entity.OrderDetail;
import com.cjmmy.vxordersystem.entity.OrderMaster;
import com.cjmmy.vxordersystem.entity.ProductInfo;
import com.cjmmy.vxordersystem.enums.ExceptionStatusEnums;
import com.cjmmy.vxordersystem.enums.OrderStatusEnums;
import com.cjmmy.vxordersystem.enums.PayStatusEnums;
import com.cjmmy.vxordersystem.exception.SystemException;
import com.cjmmy.vxordersystem.repository.OrderDetailRepository;
import com.cjmmy.vxordersystem.repository.OrderMasterRepository;
import com.cjmmy.vxordersystem.utils.KeyUtil;
import com.cjmmy.vxordersystem.utils.TransferUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMasterRepository orderMasterRepository;
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Autowired
    private ProductInfoServiceImpl productInfoService;
    /**
     * 创建订单
     * 前段是不可能把任何数据都传过来给我们用的，比如价格、库存这些属性需要我们去查数据库来确定而不是有前端传过来的数据确定
     * 创建以后还要计算总额，改库存，存入数据库等
     * @param orderDTO
     * @return
     */
    /**
     * 因为这个方法有操作数据库，所以如果有问题要进行回滚
     * @param orderDTO
     * @return
     */
    @Transactional
    public OrderDTO createOrder(OrderDTO orderDTO) {
//        一开始就生成orderId
        String orderId = KeyUtil.generateKey();
//        获取到订单中所有单个商品的订单详情
        List<OrderDetail> orderDetailList = orderDTO.getOrderDetailList();
        BigDecimal orderAmount = new BigDecimal(BigInteger.ZERO);
//        遍历每一件商品产看商品是否存在，计算订单总金额
        for (OrderDetail orderDetail : orderDetailList) {
            ProductInfo productInfo = productInfoService.findOne(orderDetail.getProductId());
//            int stock = productInfo.getProductStock();
//            if(stock<orderDetail.getProductQuantity()){
//                throw new SystemException(ExceptionStatusEnums.STOCK_NOT_ENOUGH);
//            }
//            stock = stock - orderDetail.getProductQuantity();
//            productInfo.setProductStock(stock);
//            productInfoRepository.save(productInfo);
            if (productInfo == null) {
                throw new SystemException(ExceptionStatusEnums.PRODUCT_NOT_EXIT);
            }
            //BigDecimal的计算方式
            orderAmount = productInfo.getProductPrice()
                    .multiply(new BigDecimal(orderDetail.getProductQuantity()))
                    .add(orderAmount);
            //订单详情入库----前端只传过来购买数量和商品id，其他数据并不全，所以有些需要我们自己添加
            orderDetail.setOrderId(orderId);
            orderDetail.setDetailId(KeyUtil.generateKey());
//            其他属性值基本都是product中的，所以只用工具复制赋值
            BeanUtils.copyProperties(productInfo,orderDetail);
            orderDetailRepository.save(orderDetail);
        }
//        订单入库
        OrderMaster orderMaster = new OrderMaster();
        //拷贝要最先执行，不然放在最后会将前面的赋值覆盖掉，其实因为orderDTO只有productId和productQuantity有值，其他都为null，覆盖发生会将字段设置是
//        非null的变为null报错
        BeanUtils.copyProperties(orderDTO,orderMaster);
        orderMaster.setOrderId(orderId);
        orderMaster.setOrderAmount(orderAmount);
        //这两个字段创建时有默认值但被覆盖为null
        orderMaster.setOrderStatus(OrderStatusEnums.NEW.getCode());
        orderMaster.setPayStatus(PayStatusEnums.WAIT.getCode());
        orderMasterRepository.save(orderMaster);
        //扣库存 非得在最后一步执行，写成这样，这个逻辑不是很清楚
        List<CartDTO> cartDTOList = orderDetailList.stream().map(e ->
            new CartDTO(e.getProductId(),e.getProductQuantity())
        ).collect(Collectors.toList());
        productInfoService.decreaseStock(cartDTOList);
        return orderDTO;
    }

    @Override
    public OrderDTO findOne(String orderId) {
        OrderMaster one = orderMasterRepository.findById(orderId).get();
        if(one==null){
            throw new SystemException(ExceptionStatusEnums.ORDER_NOT_EXIT);
        }
        List<OrderDetail> orderDetailList = orderDetailRepository.findByOrderId(one.getOrderId());
        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(one,orderDTO);
        orderDTO.setOrderDetailList(orderDetailList);
        return orderDTO;
    }

    /**
     * 我们要通过buyerOpenId获取Page<OrderDTO>，现在我们通过buyerOpenId能查到Page<OrderMaster>，所以我们需要将
     * Page<OrderMaster>转为Page<OrderDTO>
     * 转换方式是new PageImpl(List，Pageable，long)所以我们还得先把Page<OrderMaster>转为List<OrderMaster>
     * List<OrderMaster>转为List<OrderDTO>,List<OrderDTO>作为参数传入转成Page<OrderDTO>
     * 所以，最主要是将单个OrderMaster转为OrderDTO，自定义一个工具
     * @param buyerOpenId
     * @param pageable
     * @return
     */
    public Page<OrderDTO> findList(String buyerOpenId, Pageable pageable) {
        Page<OrderMaster> orderMasterPage = orderMasterRepository.findByBuyerOpenid(buyerOpenId, pageable);
        List<OrderDTO> orderDTOList = TransferUtil.orderMasterList2OrderDTOList(orderMasterPage.getContent());
//        java8 lamda遍历list并执行相关操作的写法  list.stream().map(e->函数(e)).collect(collectors.toList())
//        orderMasterPage.getContent().stream().map(e -> TransferUtil.orderMaster2OrderDTO(e)
//        ).collect(Collectors.toList());
        return new PageImpl<OrderDTO>(orderDTOList,pageable,orderMasterPage.getTotalElements());

    }

    @Override
    @Transactional
    public OrderDTO cancel(OrderDTO orderDTO) {
        //因为要将订单存入数据库，但是OrderDTO是存不进去的，所以先得将OrderDTO转成OrderMaster
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO,orderMaster);
        //判断订单状态能否取消，如果订单已经完成或者订单已经取消，则不能取消
        if(!orderDTO.getOrderStatus().equals(OrderStatusEnums.NEW.getCode())){
            log.error("【取消订单】 订单状态不正确，无法取消 orderStatus={}, orderId={}"+orderDTO.getOrderStatus(),orderDTO.getOrderId());
            throw new SystemException(ExceptionStatusEnums.ORDER_STATUS_ERROR);
        }
        //更该订单状态并存如数据库
        orderMaster.setOrderStatus(OrderStatusEnums.CANCEL.getCode());
        OrderMaster saveResult = orderMasterRepository.save(orderMaster);
        if(saveResult == null) {
            log.error("【取消订单】 取消订单出现异常 orderMaster={}"+orderMaster);
            throw new SystemException(ExceptionStatusEnums.ORDER_STATUS_FAIL);
        }
        //更改相关商品库存
        List<OrderDetail> orderDetailList = orderDTO.getOrderDetailList();
        List<CartDTO> cartDTOList = orderDetailList.stream().map(e -> new CartDTO(e.getProductId(), e.getProductQuantity())).collect(Collectors.toList());
        productInfoService.increaseStock(cartDTOList);
        //如果已经支付，需要进行退款
        if(orderDTO.getPayStatus().equals(PayStatusEnums.SUCCESS.getCode())){
            //TODO
        }
        //最后将订单返回
        orderDTO.setOrderStatus(OrderStatusEnums.CANCEL.getCode());
        return orderDTO;
    }

    @Override
    @Transactional
    public OrderDTO finish(OrderDTO orderDTO) {
        //首先判断订单状态可否执行
        if(!orderDTO.getOrderStatus().equals(OrderStatusEnums.NEW.getCode())){
            log.error("【完成订单】 订单状态不正确,不可完成 orderStatus={},orderId={}"+orderDTO.getOrderStatus(),orderDTO.getOrderId());
            throw new SystemException(ExceptionStatusEnums.ORDER_STATUS_ERROR);
        }
        orderDTO.setOrderStatus(OrderStatusEnums.FINISHED.getCode());
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO,orderMaster);
        OrderMaster saveResult = orderMasterRepository.save(orderMaster);
        if(saveResult == null ){
            log.error("【完成订单】 完成订单异常，订单状态未成功保存 orderMaster={}"+orderDTO);
            throw new SystemException(ExceptionStatusEnums.ORDER_STATUS_FAIL);
        }

        return orderDTO;
    }

    @Override
    public OrderDTO pay(OrderDTO orderDTO) {
        //首先判断订单状态，新订单才需要支付
        if(!orderDTO.getOrderStatus().equals(OrderStatusEnums.NEW.getCode())){
            log.error("【订单支付】 订单状态不正确,不可支付 orderStatus={},orderId={}"+orderDTO.getOrderStatus(),orderDTO.getOrderId());
            throw new SystemException(ExceptionStatusEnums.ORDER_STATUS_ERROR);
        }
        //判断支付状态
        if(!orderDTO.getPayStatus().equals(PayStatusEnums.WAIT.getCode())){
            log.error("【订单支付】 支付状态不正确 payStatus={},orderId={"+orderDTO.getPayStatus(),orderDTO.getOrderId());
            throw new SystemException(ExceptionStatusEnums.PAY_STATUS_ERROR);
        }
        //修改状态
        orderDTO.setPayStatus(PayStatusEnums.SUCCESS.getCode());
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO,orderMaster);
        OrderMaster saveResult = orderMasterRepository.save(orderMaster);
        if(saveResult == null) {
            log.error("【订单支付】 订单支付异常，支付状态未成功保存 payMaster={}"+orderDTO.getPayStatus());
            throw new SystemException(ExceptionStatusEnums.PAY_STATUS_FAIL);
        }
        return orderDTO;
    }
}
