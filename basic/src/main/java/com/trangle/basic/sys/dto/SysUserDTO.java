package com.trangle.basic.sys.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 系统用户DTO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SysUserDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户账号（幂等校验使用）
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
     * 操作人ID
     */
    private Long operatorId;

    /**
     * 操作人名称
     */
    private String operatorName;
}
