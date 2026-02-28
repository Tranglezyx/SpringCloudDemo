package com.trangle.basic.sys.controller;

import com.alibaba.fastjson2.JSON;
import com.github.pagehelper.PageInfo;
import com.trangle.basic.common.dto.BaseResponse;
import com.trangle.basic.common.enums.KafkaTopicEnum;
import com.trangle.basic.common.service.KafkaProducerService;
import com.trangle.basic.common.util.IdempotentUtil;
import com.trangle.basic.sys.dto.SysUserDTO;
import com.trangle.basic.sys.dto.SysUserSaveMessage;
import com.trangle.basic.sys.entity.SysUser;
import com.trangle.basic.sys.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * <p>
 * 系统用户表 前端控制器
 * </p>
 *
 * @author trangle
 * @since 2024-01-01
 */
@Slf4j
@RestController
@RequestMapping("/sys-user")
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private IdempotentUtil idempotentUtil;

    @Autowired
    private KafkaProducerService kafkaProducerService;

    /**
     * 新增用户（带幂等校验和Kafka消息发送）
     *
     * @param sysUserDTO 用户DTO
     * @return BaseResponse
     */
    @PostMapping("/save-with-idempotent")
    public BaseResponse<String> saveWithIdempotent(@RequestBody @Validated SysUserDTO sysUserDTO) {
        log.info("收到用户保存请求, account: {}", sysUserDTO.getAccount());

        // 1. 幂等校验
        // 使用account作为幂等key，防止重复提交
        String idempotentKey = "sys_user_save:" + sysUserDTO.getAccount();
        boolean isFirstRequest = idempotentUtil.checkAndSetIdempotent(idempotentKey);

        if (!isFirstRequest) {
            log.warn("检测到重复请求, account: {}", sysUserDTO.getAccount());
            return BaseResponse.error("请勿重复提交");
        }

        // 2. 构建消息
        String messageId = UUID.randomUUID().toString();
        SysUserSaveMessage message = SysUserSaveMessage.builder()
                .messageId(messageId)
                .account(sysUserDTO.getAccount())
                .mobile(sysUserDTO.getMobile())
                .password(sysUserDTO.getPassword())
                .nickName(sysUserDTO.getNickName())
                .createTime(LocalDateTime.now())
                .operatorId(sysUserDTO.getOperatorId())
                .operatorName(sysUserDTO.getOperatorName())
                .build();

        // 3. 发送消息到Kafka
        String topic = KafkaTopicEnum.SYS_USER_SAVE.getTopicName();
        String key = sysUserDTO.getAccount(); // 使用account作为key，保证同一用户的消息发送到同一分区
        
        boolean sendSuccess = kafkaProducerService.sendMessage(topic, key, message);

        if (sendSuccess) {
            log.info("用户保存消息发送成功, account: {}, messageId: {}", 
                    sysUserDTO.getAccount(), messageId);
            return BaseResponse.success(messageId, "用户保存请求已提交，正在处理中");
        } else {
            log.error("用户保存消息发送失败, account: {}", sysUserDTO.getAccount());
            // 删除幂等key，允许重新提交
            idempotentUtil.removeIdempotentKey(idempotentKey);
            return BaseResponse.error("系统繁忙，请稍后重试");
        }
    }

    /**
     * 新增用户（普通方式，直接保存到数据库）
     *
     * @param sysUser 用户信息
     * @return BaseResponse
     */
    @PostMapping("/save")
    public BaseResponse<Boolean> save(@RequestBody SysUser sysUser) {
        sysUser.setCreateTime(System.currentTimeMillis());
        sysUser.setUpdateTime(System.currentTimeMillis());
        boolean save = sysUserService.save(sysUser);
        return BaseResponse.success(save);
    }

    /**
     * 分页查询
     *
     * @param pageNum
     * @param pageSize
     * @param account
     * @param mobile
     * @return
     */
    @GetMapping("/page")
    public BaseResponse<PageInfo<SysUser>> page(@RequestParam(defaultValue = "1") int pageNum,
                                                @RequestParam(defaultValue = "10") int pageSize,
                                                @RequestParam(required = false) String account,
                                                @RequestParam(required = false) String mobile) {
        return BaseResponse.success(sysUserService.page(pageNum, pageSize, account, mobile));
    }

    /**
     * 根据ID查询用户
     *
     * @param id 用户ID
     * @return BaseResponse
     */
    @GetMapping("/get/{id}")
    public BaseResponse<SysUser> getById(@PathVariable("id") Long id) {
        SysUser user = sysUserService.getById(id);
        return BaseResponse.success(user);
    }

    /**
     * 更新用户
     *
     * @param sysUser 用户信息
     * @return BaseResponse
     */
    @PutMapping("/update")
    public BaseResponse<Boolean> update(@RequestBody SysUser sysUser) {
        sysUser.setUpdateTime(System.currentTimeMillis());
        boolean update = sysUserService.updateById(sysUser);
        return BaseResponse.success(update);
    }

    /**
     * 删除用户
     *
     * @param id 用户ID
     * @return BaseResponse
     */
    @DeleteMapping("/delete/{id}")
    public BaseResponse<Boolean> delete(@PathVariable("id") Long id) {
        boolean remove = sysUserService.deleteById(id);
        return BaseResponse.success(remove);
    }
}
