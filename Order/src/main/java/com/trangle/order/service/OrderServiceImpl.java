package com.trangle.order.service;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.trangle.order.dto.PayMoneyDTO;
import com.trangle.order.dto.SubGoodsNumDTO;
import com.trangle.order.entity.OrderBasic;
import com.trangle.order.feign.PayFeign;
import com.trangle.order.feign.StorageFeign;
import com.trangle.order.mapper.OrderBasicMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * @author trangle
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Resource
    private PayFeign payFeign;
    @Resource
    private StorageFeign storageFeign;

    @Resource
    private OrderBasicMapper orderBasicMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addOrder(OrderBasic orderBasic) {
        String orderId = UUID.randomUUID().toString();
        orderBasic.setOrderId(orderId);
        orderBasic.setOrderDate(LocalDateTime.now());
        orderBasicMapper.insert(orderBasic);
//        PayMoneyDTO payMoneyDTO = new PayMoneyDTO(orderBasic.getUserId(), orderId, orderBasic.getMoney());
//        payFeign.payMoney(payMoneyDTO);
//        SubGoodsNumDTO subGoodsNumDTO = new SubGoodsNumDTO(orderBasic.getGoodsId(), orderBasic.getGoodsNum());
//        storageFeign.subGoodsNum(subGoodsNumDTO);
    }

    @Override
    public List<OrderBasic> getOrderList() {
        return orderBasicMapper.selectList(Wrappers.lambdaQuery());
    }
}
