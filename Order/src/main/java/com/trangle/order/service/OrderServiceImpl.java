package com.trangle.order.service;

import com.trangle.dto.PayMoneyDTO;
import com.trangle.order.feign.PayFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * @author trangle
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private PayFeign payFeign;

    @Override
    public Boolean payMoney(Long id) {
        PayMoneyDTO payMoneyDTO = new PayMoneyDTO(id, BigDecimal.valueOf(Math.random() * 100));
        return payFeign.payMoney(payMoneyDTO);
    }
}
