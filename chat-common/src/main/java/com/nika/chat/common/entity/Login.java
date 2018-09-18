package com.nika.chat.common.entity;

import lombok.Data;

/**
 * @Date 16:12 2018/09/13
 * @Author Nika
 */
@Data
public class Login {

    private Integer userId;

    private String userName;

    private String password;

    private String token;

    public Login(Integer userId, String userName, String token) {
        this.userId = userId;
        this.userName = userName;
        this.token = token;
    }
}
