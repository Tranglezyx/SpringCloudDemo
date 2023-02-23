package com.trangle.pay.service;

import com.trangle.pay.entity.PayBasic;

import java.math.BigDecimal;

public interface PayService {

    void addUserPay(PayBasic payBasic);

    void addUserPayMoney(PayBasic payBasic);

    void subUserMoney(PayBasic payBasic);

    BigDecimal getMoney(PayBasic payBasic);
}
