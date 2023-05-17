package com.trangle.order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.trangle.order.entity.OrderBasic;
import org.apache.ibatis.annotations.Param;

public interface OrderBasicMapper extends BaseMapper<OrderBasic> {

    OrderBasic selectByOrderId(@Param("orderId") String orderId);
}
