package com.trangle.order.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.apache.commons.lang3.RandomUtils;

import java.time.LocalDateTime;

@TableName("update_on_unique_key_demo")
@Data
public class UpdateOnUniqueKeyDemo {

    @TableId(type = IdType.AUTO)
    private Long id;
    private String batchId;
    private String phone;
    private LocalDateTime createTime;
    private String content;

    public UpdateOnUniqueKeyDemo() {
        this.setBatchId(String.valueOf(RandomUtils.nextInt(0, 10)));
        this.setPhone(String.valueOf(RandomUtils.nextInt(10, 20)));
        this.setContent(String.valueOf(RandomUtils.nextInt(0, 10000)));
        this.setCreateTime(LocalDateTime.now());
    }
}
