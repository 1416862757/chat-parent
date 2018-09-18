package com.nika.chat.common.entity;

import lombok.Data;

/**
 * @Date 10:01 2018/09/14
 * @Author Nika
 */
@Data
public class MsgData {
    // 发送方用户名
    private String fromUserName;

    // 接收方用户名
    private String toUserName;

    // 消息类型  默认字符串
    private Integer type = 1;

    //消息内容
    private String context;
}
