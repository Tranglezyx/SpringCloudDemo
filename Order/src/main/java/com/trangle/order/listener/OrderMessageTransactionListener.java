package com.trangle.order.listener;

import com.alibaba.fastjson.JSONObject;
import com.trangle.order.entity.OrderBasic;
import com.trangle.order.mapper.OrderBasicMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.springframework.messaging.Message;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.Resource;

@Slf4j
@RocketMQTransactionListener
public class OrderMessageTransactionListener implements RocketMQLocalTransactionListener {

    @Resource
    private OrderBasicMapper orderBasicMapper;
    @Resource
    private TransactionTemplate transactionTemplate;

    @Override
    public RocketMQLocalTransactionState executeLocalTransaction(Message message, Object o) {
        String msgStr = new String((byte[]) message.getPayload());
        OrderBasic orderBasic = JSONObject.parseObject(msgStr, OrderBasic.class);
        log.info("准备提交本地事务：{}", msgStr);
        //放到同一个本地事务中
        this.transactionTemplate.executeWithoutResult(status -> {
            orderBasicMapper.insert(orderBasic);
        });
        return RocketMQLocalTransactionState.COMMIT;
    }

    @Override
    public RocketMQLocalTransactionState checkLocalTransaction(Message message) {
        String msgStr = new String((byte[]) message.getPayload());
        OrderBasic orderBasic = JSONObject.parseObject(msgStr, OrderBasic.class);
        log.info("事务消息确认本地事务,orderId:{}", orderBasic.getOrderId());
        OrderBasic result = orderBasicMapper.selectByOrderId(orderBasic.getOrderId());
        if (result == null) {
            try {
                //放到同一个本地事务中
                this.transactionTemplate.executeWithoutResult(status -> {
                    orderBasicMapper.insert(orderBasic);
                });
            } catch (Exception e) {
                log.error("保存订单失败", e);
                return RocketMQLocalTransactionState.ROLLBACK;
            }
        }
        return RocketMQLocalTransactionState.COMMIT;
    }
}
