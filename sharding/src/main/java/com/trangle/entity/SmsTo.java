package com.trangle.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

/*
create table t_sms_to
(
    id               bigint       null,
    submit_date      date         null,
    submit_date_time datetime     null,
    phone            varchar(20)  null,
    content          varchar(200) null
);
 */
@TableName("t_sms_to")
@Data
public class SmsTo {

    @TableId(type = IdType.NONE)
    private Long id;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date submitDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:dd")
    private Date submitDateTime;

    private String phone;

    private String content;
}
