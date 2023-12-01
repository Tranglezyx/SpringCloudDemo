package com.trangle.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@TableName("t_sms_to")
@Data
public class SmsTo {

    private Long id;

    private Integer submitDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:dd")
    private LocalDateTime submitDateTime;

    private String phone;
}
