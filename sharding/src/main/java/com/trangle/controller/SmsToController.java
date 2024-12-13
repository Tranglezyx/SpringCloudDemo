package com.trangle.controller;

import cn.hutool.core.lang.Snowflake;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.trangle.entity.SmsTo;
import com.trangle.mapper.SmsToMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/smsTo")
@Slf4j
public class SmsToController {

    @Resource
    private SmsToMapper smsToMapper;

    @PostMapping("/insert")
    public String insert(@RequestBody SmsTo smsTo) {
        smsTo.setId(new Snowflake().nextId());
        smsToMapper.insert(smsTo);
        return "success";
    }

    @PostMapping("/select")
    public List<SmsTo> select(@RequestBody SmsTo smsTo) {
        return smsToMapper.selectList(Wrappers.<SmsTo>lambdaQuery()
                .ge(SmsTo::getSubmitDate, smsTo.getSubmitDate())
                .le(SmsTo::getSubmitDate, smsTo.getSubmitDate())
                .eq(StringUtils.isNotBlank(smsTo.getPhone()), SmsTo::getPhone, smsTo.getPhone()));
    }
}
