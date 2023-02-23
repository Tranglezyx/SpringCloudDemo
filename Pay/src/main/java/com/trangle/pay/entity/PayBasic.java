package com.trangle.pay.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;

@Data
@TableName("pay_basic")
public class PayBasic {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    /**
     * id
     */
    private Long id;

    /**
     * user_id
     */
    private Long userId;

    /**
     * user_name
     */
    private String userName;

    /**
     * money
     */
    private BigDecimal money;

    public PayBasic() {}
}
