package com.trangle.storage.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("storage_basic")
public class StorageBasic {

    @TableId(type = IdType.AUTO)
    /**
     * id
     */
    private Long id;

    /**
     * goods_name
     */
    private String goodsName;

    /**
     * goods_id
     */
    private Long goodsId;

    /**
     * num
     */
    private Integer num;
}
