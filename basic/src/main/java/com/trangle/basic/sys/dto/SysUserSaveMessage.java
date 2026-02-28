package com.trangle.basic.sys.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 用户保存消息DTO
 * 用于Kafka消息传输
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SysUserSaveMessage implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 消息ID（用于幂等校验）
     */
    private String messageId;

    /**
     * 用户账号
     */
    private String account;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 密码
     */
    private String password;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 消息创建时间
     */
    private LocalDateTime createTime;

    /**
     * 操作人ID
     */
    private Long operatorId;

    /**
     * 操作人名称
     */
    private String operatorName;
}
