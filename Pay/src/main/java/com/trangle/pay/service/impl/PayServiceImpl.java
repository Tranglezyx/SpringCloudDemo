package com.trangle.pay.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.trangle.pay.entity.PayBasic;
import com.trangle.pay.mapper.PayBasicMapper;
import com.trangle.pay.mapper.PayDetailMapper;
import com.trangle.pay.service.PayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;

@Service
@Slf4j
public class PayServiceImpl implements PayService {

    @Resource
    private PayBasicMapper payBasicMapper;
    @Resource
    private PayDetailMapper payDetailMapper;

    @Override
    public void addUserPay(PayBasic payBasic) {
        payBasicMapper.insert(payBasic);
    }

    @Override
    public void addUserPayMoney(PayBasic payBasic) {
        payBasicMapper.update(null, Wrappers.<PayBasic>lambdaUpdate()
                .set(PayBasic::getMoney, payBasic.getMoney())
                .eq(PayBasic::getUserId, payBasic.getUserId()));
    }

    @Override
    public void subUserMoney(PayBasic payBasic) {
        PayBasic basic = payBasicMapper.selectOne(Wrappers.<PayBasic>lambdaQuery()
                .eq(PayBasic::getUserId, payBasic.getUserId())
                .last(" limit 1"));
        if (basic.getMoney().compareTo(payBasic.getMoney()) >= 0) {
            payBasicMapper.update(null, Wrappers.<PayBasic>lambdaUpdate()
                    .set(PayBasic::getMoney, basic.getMoney().subtract(payBasic.getMoney()))
                    .eq(PayBasic::getUserId, payBasic.getUserId()));
        } else {
            log.error("余额不足，userId:{}", payBasic.getUserId());
        }
    }

    @Override
    public BigDecimal getMoney(PayBasic payBasic) {
        PayBasic basic = payBasicMapper.selectOne(Wrappers.<PayBasic>lambdaQuery()
                .eq(PayBasic::getUserId, payBasic.getUserId())
                .last(" limit 1"));
        if (basic != null) {
            return basic.getMoney();
        }
        return null;
    }
}
