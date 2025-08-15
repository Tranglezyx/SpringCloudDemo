package com.trangle.disruptor.model;

import lombok.Data;

import java.util.List;

/**
 * @Author: zhouyunxiang
 * @Date: 2025/8/13 11:23
 * @Description:
 */
@Data
public class CmppSmsDto {

    /**
     * 客户cmpp帐号
     */
    private String spId;

    /**
     * 目标号码
     */
    private String destId;

    /**
     * 短信内容
     */
    private String content;

    /**
     * 该条短信唯一标识
     */
    private String msgId;

    /**
     * 短信批次唯一的标识
     */
    private String uhId;

    /**
     * 编码类型
     */
    private String msgFmt;

    /**
     * 扩展号码
     */
    private String srcId;

    /**
     * 长短信总条数
     */
    private Integer pkTotal;

    /**
     * 长短信当前序号
     */
    private Integer pkNum;

    /**
     * 通道连接ID
     */
    private String channelId;

    /**
     * 请求ID
     */
    private String sequenceId;

    /**
     * 发送方式
     * SendByEnum
     */
    private String sendBy;

    /**
     * 模版ID
     */
    private String templateId;

    /**
     * 变量
     */
    private String params;

    /**
     * 变量集合
     */
    private List<String> paramsList;

    /**
     * 发送时间
     */
    private String sendTime;

    /**
     * 定时发送时间
     */
    private Long timingTime;

    /**
     * 批次ID
     */
    private String batchId;

    /**
     * 子账号ID
     */
    private Long accountId;

    /**
     * 是否是上行
     */
    private boolean mo;

    /**
     * 上行通道
     */
    private String moChannelId;
    /**
     * 定时短信标记
     */
    private Integer timeFlag;

    /**
     * 是否测试短信
     */
    private Integer isChannelTest;

    /**
     * 用户ID
     */
    private Long portalUserId;

    /**
     * 是否签名发送测试
     */
    private Boolean isSignSendTest;
    /**
     * 客户smsId
     */
    private String customSmsId;
}
